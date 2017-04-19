package com.softtek.mdm.exception.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class LogAndDispatchExceptionHandlerExceptionResolver extends SimpleMappingExceptionResolver {


	/**
	 * The default <tt>ExceptionHandlerExceptionResolver</tt> has order MAX_INT (lowest priority - see
	 * {@link Ordered#LOWEST_PRECEDENCE). The constructor gves this slightly higher precedence so it runs first. Also
	 * enable logging to this classe's logger by default.
	 */
	public LogAndDispatchExceptionHandlerExceptionResolver() {
		// Turn logging on by default
		setWarnLogCategory(getClass().getName());

		// Make sure this handler runs before the default
		// ExceptionHandlerExceptionResolver
		setOrder(LOWEST_PRECEDENCE - 1);
	}

	/**
	 * Override the default to generate a log message with dynamic content.
	 * 
	 * @param e Exception
	 * @param req HttpServletRequest
	 * @return -
	 */
	@Override
	public String buildLogMessage(Exception e, HttpServletRequest req) {
		return "MVC exception: " + e.getMessage();
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView mav = super.doResolveException(request, response, handler, ex);

		// Make more information available to the view
		mav.addObject("exception", ex);
		mav.addObject("url", request.getRequestURL());
		mav.addObject("timestamp", new DateTime());
		mav.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR);
		return mav;
	}
}
