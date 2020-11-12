package com.empresa.api.exceptionhandler;

import java.time.DateTimeException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.empresa.api.exceptionhandler.Problem.ProblemBuilder;
import com.empresa.api.validation.ValidacaoException;
import com.empresa.domain.exception.EntidadeEmUsoException;
import com.empresa.domain.exception.EntidadeNaoEcontradaException;
import com.empresa.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	
	private static final String MSG_ERRO_GENERICO_USUARIO_FINAL = 
			"Ocorreu um erro interno inesperado no sistema. Tente novamente "
			+ "e se o problema persistir, entre em contato com o administrador do sistema";
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(EntidadeNaoEcontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(
			EntidadeNaoEcontradaException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
		
		Problem problem = criarProblema(detail, MSG_ERRO_GENERICO_USUARIO_FINAL, status, problemType)
			.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException (
			EntidadeEmUsoException ex, WebRequest request){
		

		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();
		
		Problem problem = criarProblema(detail, detail, status, problemType)
			.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.NEGOCIO_EXCEPTION;
		String detail = ex.getMessage();
		
		Problem problem = criarProblema(detail, detail, status, problemType)
			.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleExceptionGeral(Exception ex, WebRequest request){
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		String detail = MSG_ERRO_GENERICO_USUARIO_FINAL;
		
		Problem problem = criarProblema(detail, MSG_ERRO_GENERICO_USUARIO_FINAL, status, problemType)
			.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido."
				+ " Corrija e informe um valor compatível com o tipo '%s'.",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		
		
		Problem problem = criarProblema(detail, MSG_ERRO_GENERICO_USUARIO_FINAL, status, problemType)
				.build();
		
		return handleExceptionInternal(ex, problem , new HttpHeaders(), status, request);

	}
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<?> handleValidacaoException(ValidacaoException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente";
		
		BindingResult bindingResult = ex.getBindingResult();
		
		List<Problem.Object> objects = extrairErrors(bindingResult);
		
		Problem problem = criarProblema(detail, detail, status, problemType)
				.fields(objects)
				.build();
		
		return handleExceptionInternal(ex, problem , new HttpHeaders(), status, request);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente";
		BindingResult bindingResult = ex.getBindingResult();
		
		List<Problem.Object> objects = extrairErrors(bindingResult);
		
		Problem problem = criarProblema(detail, detail, status, problemType)
				.fields(objects)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private List<Problem.Object> extrairErrors(BindingResult bindingResult) {
		
		List<Problem.Object> objects = bindingResult.getAllErrors().stream()
				.map(objectError -> 
					{
						String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
						
						String name = objectError.getObjectName();
						
						if(objectError instanceof FieldError) {
							name = ((FieldError) objectError).getField();
						}
						
						return Problem.Object.builder()
						.name(name)
						.userMessage(message)
						.build();
					})
			.collect(Collectors.toList());
		return objects;
	}
	
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente.", ex.getRequestURL());
		
		Problem problem = criarProblema(detail, MSG_ERRO_GENERICO_USUARIO_FINAL, status, problemType)
			.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable cause = ExceptionUtils.getRootCause(ex);
		
		if (cause instanceof DateTimeException) {
			return handleDateTimeException((DateTimeException) cause, new HttpHeaders(), status, request);
		}else if(cause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) cause, new HttpHeaders(), status, request);
		} else if(cause instanceof IgnoredPropertyException) {
			return handleIgnoredPropertyException((IgnoredPropertyException) cause, new HttpHeaders(), status, request);
		} else if (cause instanceof UnrecognizedPropertyException) {
			return handleUnrecognizedPropertyException((UnrecognizedPropertyException) cause, new HttpHeaders(), status, request);
		}
		
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "A Mensagem está incompreensível, verifique o corpo da requisição.";
		
		Problem problem = criarProblema(detail, MSG_ERRO_GENERICO_USUARIO_FINAL, status, problemType)
			.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	private ResponseEntity<Object> handleDateTimeException(DateTimeException ex, HttpHeaders httpHeaders,
			HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = ex.getMessage();
		
		
		Problem problem = criarProblema(detail, MSG_ERRO_GENERICO_USUARIO_FINAL, status, problemType)
				.build();
		
		return handleExceptionInternal(ex, problem , new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex,
			HttpHeaders httpHeaders, HttpStatus status, WebRequest request) {
		
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' não existe.",
				path);
		
		
		Problem problem = criarProblema(detail, MSG_ERRO_GENERICO_USUARIO_FINAL, status, problemType)
				.build();
		
		return handleExceptionInternal(ex, problem , new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleIgnoredPropertyException(IgnoredPropertyException ex,
			HttpHeaders httpHeaders, HttpStatus status, WebRequest request) {
		
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("Não é permitido informar a propriedade '%s'. ",
				path);
		
		
		Problem problem = criarProblema(detail, MSG_ERRO_GENERICO_USUARIO_FINAL, status, problemType)
				.build();
		
		return handleExceptionInternal(ex, problem , new HttpHeaders(), status, request);
		
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path = ex.getPath().stream()
			.map(ref -> ref.getFieldName())
			.collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		
		Problem problem = criarProblema(detail, MSG_ERRO_GENERICO_USUARIO_FINAL, status, problemType)
				.build();
		
		return handleExceptionInternal(ex, problem , new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if(body == null) {
			body = Problem.builder()
					.detail(status.getReasonPhrase())
					.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
					.status(status.value())
					.build();
		} else if(body instanceof String) {
			body = Problem.builder()
					.detail((String) body)
					.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private static ProblemBuilder criarProblema(String detail, String userMessage, HttpStatus status, ProblemType problemType) {
		
		return Problem.builder()
				.detail(detail)
				.userMessage(userMessage)
				.status(status.value())
				.title(problemType.getTitle())
				.type(problemType.getUri());
	}
}
