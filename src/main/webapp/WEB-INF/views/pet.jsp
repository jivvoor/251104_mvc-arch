<%--
    Repository나 POST 관련된 처리 등을 Controller로 로직을 옮기고,
    View(UI) jsp는 레이아웃이나 디자인 자체에 집중 -> MVC2
--%>
<%--<%@ page import="org.example.mvc.model.repository.InMemoryPetRepository" %>--%>
<%@ page import="org.example.mvc.model.entity.Pet" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- JSP가 만들어질 때 1번만 선언되게 된다 -> 내부에 있는 petList는 유지 --%>
<%--<%! PetRepository repository = new PetRepository(); %>--%>
<html>
<head>
    <title>반려동물을 소개합니다</title>
</head>
<body>
<%-- post 시 처리하는 로직 --%>
<%--    <%--%>
<%--        if (request.getMethod().equals("POST")) {--%>
<%--            System.out.println("POST 요청");--%>
<%--            // form에서 input으로 넣은 걸 가지고 있음--%>
<%--            System.out.println(request.getParameter("name"));--%>
<%--            System.out.println(request.getParameter("age"));--%>
<%--            System.out.println(request.getParameter("category"));--%>
<%--            String name = request.getParameter("name");--%>
<%--            int age = Integer.parseInt(request.getParameter("age")); // String -> int (변환)--%>
<%--            String category = request.getParameter("category");--%>
<%--            Pet pet = new Pet(name, age, category);--%>
<%--            repository.save(pet);--%>
<%--        }--%>
<%--    %>--%>

<%-- form --%>
<form method="post">
    <h3>새로운 반려동물</h3>
    이름 : <input type="text" name="name"><br>
    나이 : <input type="number" name="age"><br>
    종류 : <input type="text" name="category"><br>
    <button>등록</button>
</form>

<%-- view -> model -> pet -> view --%>
<%--    <% List<Pet> petList = repository.findAll(); %>--%>
<div style="display: flex; flex-wrap: wrap; gap: 16px">
    <%--        <% for (Pet pet : petList) { %>--%>
    <% for (Pet pet : (List<Pet>) request.getAttribute("petList")) { %>
    <p>
        이름 : <%= pet.name() %><br>
        나이 : <%= pet.age() %><br>
        종류 : <%= pet.category() %>
    </p>
    <% } %>
</div>
</body>
</html>