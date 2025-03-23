<div class="container py-4">
    <h2>View Movie</h2>
    <c:choose>
        <c:when test="${empty movie}">
            <p class="lead">Movie not found</p>
        </c:when>
        <c:otherwise>
            <div class="row mt-4">
                <div class="col-md-6 offset-md-3">
                    <div class="card">
                        <div class="card-header">
                            <h4 class="text-center">${movie.title}</h4>
                        </div>
                        <div class="card-body">
                            <img src="${posterURL}" alt="poster" class="img-fluid mb-3 mx-auto d-block" style="max-width: 100%; height: auto;"/>
                            <ul class="list-group">
                                <li class="list-group-item"><strong>Genre:</strong> ${movie.genre}</li>
                                <li class="list-group-item"><strong>Sub-Genre:</strong> ${movie.sub_genre}</li>
                                <li class="list-group-item"><strong>Release Year:</strong> ${movie.release_year}</li>
                                <li class="list-group-item"><strong>Location Name:</strong> ${movie.location_name}</li>
                                <li class="list-group-item"><strong>Format Name:</strong> ${movie.format_name}</li>
                            </ul>
                            <p class="mx-auto mt-3 text-center" style="max-width: 600px;">${plot}</p>
                        </div>
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