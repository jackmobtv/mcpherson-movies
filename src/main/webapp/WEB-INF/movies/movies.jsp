<div class="container py-4">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Movies</h2>
        <c:if test="${not empty sessionScope.activeUser && sessionScope.activeUser.privileges eq 'Admin' && sessionScope.activeUser.status eq 'active'}">
            <a href="add-movies" class="btn btn-warning float-right" role="button">Add Movie</a>
        </c:if>
    </div>
    <div class="col d-flex justify-content-between align-items-center">
        <!-- Responsive toggler START -->
        <button class="btn btn-primary d-lg-none" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasSidebar" aria-controls="offcanvasSidebar">
            <i class="bi bi-list fs-4"></i>
        </button>
        <!-- Responsive toggler END -->
    </div>
    <div class="row">
        <div class="col-lg-9">
            <div class="table-responsive small">
                <table class="table table-striped table-sm">
                    <thead>
                    <tr>
                        <th scope="col">Title</th>
                        <th scope="col">Genre</th>
                        <th scope="col">Year</th>
                        <th scope="col">Location</th>
                        <th scope="col">Format</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${movies}" var="movies">
                        <tr>
                            <td class="align-middle"><a href="${appURL}/view-movies?id=${movies.movie_id}">${movies.title}</a></td>
                            <td class="align-middle">${movies.genre}</td>
                            <td class="align-middle">${movies.release_year == 0 ? "" : movies.release_year}</td>
                            <td class="align-middle">${movies.location_name}</td>
                            <td class="align-middle">${movies.format_name}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <%@include file="/WEB-INF/fragments/movie-sidebar.jspf"%>
    </div>
</div>
