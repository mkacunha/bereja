<%-- 
    Document   : lista
    Created on : 20/02/2016, 08:57:31
    Author     : munif
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Qualificacoes</title>
    </head>
    <body style="font-family: sans-serif">
        <h1>Cadastro de Qualificacoes</h1>
        <a href="controlador?acao=criar">Novo</a>
        <table border="1">
            <thead>
                <tr>
                    <th>Data</th>
                    <th>Texto</th>
        
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${qualificacoes}" var="q">
                    <tr>
                        <td>${q.quando}</td>
                        <td>${q.texto}</td>
                        
                        
                        <td>
                            <a href="controlador?acao=excluir&id=${q.id}">Excluir</a>
                            <a href="controlador?acao=alterar&id=${q.id}">Editar</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>



        

</body>
</html>
