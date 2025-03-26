<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center">
        <h2>View Movie</h2>
        <c:if test="${not empty sessionScope.activeUser && sessionScope.activeUser.privileges eq 'Admin' && sessionScope.activeUser.status eq 'active'}">
            <a class="btn btn-warning float-right" href="${appURL}/update-movies?id=${movie.movie_id}">Update Movie</a>
        </c:if>
    </div>
    <c:choose>
        <c:when test="${empty movie}">
            <p class="lead">Movie not found</p>
        </c:when>
        <c:otherwise>
            <div class="row mt-4">
                <div class="col-md-6 offset-md-3">
                    <div class="d-flex justify-content-between align-items-center mb-2">
                        <a class="btn btn-outline-primary nav-button" href="${appURL}/view-movies?id=${movie.movie_id - 1}" aria-label="Previous Movie"><i class="bi bi-arrow-left"></i></a>
                        <div class="card mx-3">
                            <div class="card-header d-flex align-items-center">
                                <i class="bi bi-star me-2"></i>
                                <h4 class="flex-grow-1 text-center mb-0">${movie.title}</h4>
                            </div>
                            <div class="card-body">
                                <img src="${posterURL}" alt="poster" class="img-fluid mb-3 mx-auto d-block" style="max-width: 100%; height: auto;"/>
                                <ul class="list-group">
                                    <li class="list-group-item"><strong>Genre:</strong> ${movie.genre}</li>
                                    <li class="list-group-item"><strong>Sub-Genre:</strong> ${movie.sub_genre}</li>
                                    <li class="list-group-item"><strong>Release Year:</strong> ${movie.release_year == 0 ? "" : movies.release_year}</li>
                                    <li class="list-group-item"><strong>Location Name:</strong> ${movie.location_name}</li>
                                    <li class="list-group-item"><strong>Format Name:</strong> ${movie.format_name}</li>
                                </ul>
                                <p class="mx-auto mt-3 text-center" style="max-width: 600px;">${plot}</p>
                            </div>
                        </div>
                        <a class="btn btn-outline-primary nav-button" href="${appURL}/view-movies?id=${movie.movie_id + 1}" aria-label="Next Movie"><i class="bi bi-arrow-right"></i></a>
                    </div>
                </div>
            </div>

            <div class="table-responsive small mt-5">
                <table class="table table-striped table-sm">
                    <thead>
                    <tr>
                        <th scope="col">Actor Name</th>
                        <th scope="col" class="col-2"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${actors}" var="actor">
                        <tr>
                            <td class="align-middle"><h5>${actor.actor_name}</h5></td>
                            <td><c:if test="${(not empty sessionScope.activeUser && sessionScope.activeUser.privileges eq 'Admin' || not empty sessionScope.activeUser && sessionScope.activeUser.privileges eq 'Premium') && sessionScope.activeUser.status eq 'active'}">
                                <a href="${appURL}/view-actors?id=${actor.actor_id}" class="btn btn-outline-primary">View Movies</a>
                            </c:if></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>