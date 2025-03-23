<div class="container py-4">
    <h2>Movies Starring ${actor.actor_name}</h2>
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
                    <td class="align-middle">
                        <c:choose>
                            <c:when test="${not empty sessionScope.activeUser && sessionScope.activeUser.privileges eq 'Admin' && sessionScope.activeUser.status eq 'active'}"><a href="${appURL}/update-movies?id=${movies.movie_id}">${movies.title}</a></c:when>
                            <c:otherwise><a href="${appURL}/view-movies?id=${movies.movie_id}">${movies.title}</a></c:otherwise>
                        </c:choose>
                    </td>
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