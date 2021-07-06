package com.andreimargaritoiu.elearning.jwt

import com.andreimargaritoiu.elearning.model.models.ErrorResponse

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import java.util.ArrayList
import com.google.gson.Gson

@Component
class JwtRequestFilter : OncePerRequestFilter() {

    fun setErrorResponse(
        status: HttpStatus,
        response: HttpServletResponse,
        request: HttpServletRequest,
        ex: Throwable
    ) {
        response.status = status.value()
        response.contentType = "application/json"
        try {
            val errorResponse = ErrorResponse(response.status.toString(), ex.message, request.requestURI)
            val errorString = Gson().toJson(errorResponse)
            val out = response.writer
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            out.print(errorString)
            out.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")
        var userId: String? = null
        val decodedToken: FirebaseToken?
        if (authorizationHeader != null) {
            val jwt = authorizationHeader
            try {
                decodedToken = FirebaseAuth.getInstance().verifyIdToken(jwt)
            } catch (exception: Exception) {
                setErrorResponse(HttpStatus.UNAUTHORIZED, response, request, exception)
                return
            }
            try {
                userId = decodedToken.uid
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            if (authorizationHeader == null)
                setErrorResponse(HttpStatus.UNAUTHORIZED, response, request, Exception("MISSING_TOKEN"))
            else {
                setErrorResponse(HttpStatus.UNAUTHORIZED, response, request, Exception("INVALID_TOKEN"))
            }
            return

        }
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        authorities.add(SimpleGrantedAuthority("authority"))
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userId, null, authorities)
        usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
        chain.doFilter(request, response)
    }
}