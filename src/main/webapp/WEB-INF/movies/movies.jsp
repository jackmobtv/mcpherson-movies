<div class="container py-4">
    <c:if test="${not empty sessionScope.activeUser && sessionScope.activeUser.privileges eq 'Admin' && sessionScope.activeUser.status eq 'active'}"><a href="add-movies" class="btn btn-warning mb-3" role="button">Add Movie</a></c:if>
    <h2>Movies</h2>
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
                    <td class="align-middle">${movies.release_year}</td>
                    <td class="align-middle">${movies.location_name}</td>
                    <td class="align-middle">${movies.format_name}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
