<div class="container py-4">
    <div hidden id="favorite">${favorited}</div>
    <c:choose>
        <c:when test="${empty movie || movie eq null}">
            <h2>View Movie</h2>
            <p class="lead">Movie not found</p>
        </c:when>
        <c:otherwise>
            <div class="d-flex justify-content-between align-items-center">
                <h2>View Movie</h2>
                <input type="hidden" id="movieId" value="${movie.movie_id}"/>
                <input type="hidden" id="userId" value="${sessionScope.activeUser.userId}"/>
                <c:if test="${not empty sessionScope.activeUser && sessionScope.activeUser.privileges eq 'Admin' && sessionScope.activeUser.status eq 'active'}">
                    <a class="btn btn-warning float-right mx-2" href="${appURL}/update-movies?id=${movie.movie_id}">Update Movie</a>
                </c:if>
            </div>
            <div class="row mt-4">
                <div class="col-md-6 offset-md-3">
                    <div class="d-flex justify-content-between align-items-center mb-2">
                        <c:if test="${!mobile}"><a class="btn btn-outline-primary nav-button" href="<c:choose><c:when test="${movie.movie_id != 1}">${appURL}/view-movies?id=${movie.movie_id - 1}</c:when><c:otherwise>#</c:otherwise></c:choose>" aria-label="Previous Movie" ><i class="bi bi-arrow-left"></i></a></c:if>
                        <div class="card mx-3">
                            <div class="card-header d-flex align-items-center">
                                <c:if test="${not empty sessionScope.activeUser}">
                                    <i class="bi bi-star me-2" id="star"></i>
                                </c:if>
                                <h4 class="flex-grow-1 text-center mb-0">${movie.title}</h4>
                            </div>
                            <div class="card-body">
                                <img src="${posterURL}" alt="poster" class="img-fluid mb-3 mx-auto d-block" style="max-width: 100%; height: auto;"/>
                                <ul class="list-group">
                                    <li class="list-group-item"><strong>Genre:</strong> ${movie.genre}</li>
                                    <li class="list-group-item"><strong>Sub-Genre:</strong> ${movie.sub_genre}</li>
                                    <li class="list-group-item"><strong>Release Year:</strong> ${movie.release_year == 0 ? "" : movie.release_year}</li>
                                    <li class="list-group-item"><strong>Location Name:</strong> ${movie.location_name}</li>
                                    <li class="list-group-item"><strong>Format Name:</strong> ${movie.format_name}</li>
                                </ul>
                                <p class="mx-auto mt-3 text-center" style="max-width: 600px;">${plot}</p>
                            </div>
                        </div>
                        <c:if test="${!mobile}"><a class="btn btn-outline-primary nav-button" href="<c:choose><c:when test="${lastMovie eq 'false'}">${appURL}/view-movies?id=${movie.movie_id + 1}</c:when><c:otherwise>#</c:otherwise></c:choose>" aria-label="Next Movie"><i class="bi bi-arrow-right"></i></a></c:if>
                    </div>
                    <c:if test="${mobile}">
                        <div class="d-flex justify-content-between align-items-center">
                            <a class="btn btn-outline-primary nav-button" href="<c:choose><c:when test="${movie.movie_id != 1}">${appURL}/view-movies?id=${movie.movie_id - 1}</c:when><c:otherwise>#</c:otherwise></c:choose>" aria-label="Previous Movie" ><i class="bi bi-arrow-left"></i></a>
                            <a class="btn btn-outline-primary nav-button" href="<c:choose><c:when test="${lastMovie eq 'false'}">${appURL}/view-movies?id=${movie.movie_id + 1}</c:when><c:otherwise>#</c:otherwise></c:choose>" aria-label="Next Movie"><i class="bi bi-arrow-right"></i></a>
                        </div>
                    </c:if>
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

            <div>
                <hr>
                <c:choose>
                    <c:when test="${not empty sessionScope.activeUser}">
                        <h2>Your Rating</h2>
                        <c:choose>
                            <c:when test="${myRating != null}">
                                <form action="${appURL}/update-rating" method="post">
                                    <input type="hidden" name="movie_id" value="${movie.movie_id}">
                                    <div class="border rounded p-3 m-5">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <!-- Left side: profile info -->
                                            <div class="d-flex align-items-center">
                                                <img src="${appURL}/img/wrongturn.jpg" width="60" height="60" alt="profile picture" class="cardImage mx-2"/>
                                                <h5 class="mb-0">${sessionScope.activeUser.fullName}</h5>
                                                <p class="mb-0 mx-3 text-secondary"><i><fmt:formatDate value="${myRating.createdAtDate}"/></i></p>
                                            </div>
                                            <!-- Right side: rating -->
                                            <div class="d-flex align-items-center">
                                                <p class="mb-0 mr-2">Rating:</p>
                                                <input class="mb-0 mx-1 form-control rating <c:if test='${not empty ratingErr}'>is-invalid</c:if>" type="number" size="10" max="10" min="0" name="rating" value="${empty formRating ? myRating.rating : formRating}" required/>
                                                <h4 class="mb-0 mx-1">/10</h4>
                                            </div>
                                        </div>

                                        <div class="mt-3">
                                            <label for="comment" class="form-label">Comment</label>
                                            <textarea id="comment" class="form-control" maxlength="1000" name="comment">${empty formComment ? myRating.comment : formComment}</textarea>
                                        </div>
                                        <button class="btn btn-primary mt-2" type="submit">Update Rating</button>
                                    </div>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <div class="border rounded p-3 m-5">
                                    <form action="${appURL}/view-movies" method="POST">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <!-- Left side: profile info -->
                                            <div class="d-flex align-items-center">
                                                <img src="${appURL}/img/wrongturn.jpg" width="60" height="60" alt="profile picture" class="cardImage mx-2"/>
                                                <h5 class="mb-0">${fullName}</h5>
                                                <p class="mb-0"><i></i></p>
                                            </div>
                                            <!-- Right side: rating -->
                                            <div class="d-flex align-items-center">
                                                <p class="mb-0 mr-2">Rating:</p>
                                                <input class="mb-0 mx-1 form-control rating <c:if test='${not empty ratingErr}'>is-invalid</c:if>" type="number" size="10" max="10" min="0" name="rating" value="${rating}" required/>
                                                <h4 class="mb-0 mx-1">/10</h4>
                                            </div>
                                        </div>

                                        <div class="mt-3">
                                            <input type="hidden" name="movie_id" value="${movie.movie_id}"/>
                                            <label for="comment" class="form-label">Comment</label>
                                            <textarea id="comment" class="form-control <c:if test='${not empty commentErr}'>is-invalid</c:if>" maxlength="1000" name="comment">${comment}</textarea>
                                            <button type="submit" class="btn btn-primary mt-2">Submit</button>
                                        </div>
                                    </form>
                                </div>
                            </c:otherwise>
                        </c:choose>

                    </c:when>
                    <c:otherwise>
                        <h2 class="text-center">Please Log In To Add a Rating</h2>
                    </c:otherwise>
                </c:choose>
                <hr>
                <div class="d-flex justify-content-between align-items-center">
                    <h2>User Ratings</h2>
                    <h5 class="mb-0 float-right mx-5"><i class="bi bi-star-fill me-2"></i>Avg. Rating: ${averageRating}/10</h5>
                </div>
                <c:forEach items="${ratings}" var="rating">
                    <div class="border rounded p-3 m-5">
                        <div class="d-flex justify-content-between align-items-center">
                            <!-- Left side: profile info -->
                            <div class="d-flex align-items-center">
                                <img src="${appURL}/img/wrongturn.jpg" width="60" height="60" alt="profile picture" class="cardImage mx-2"/>
                                <h5 class="mb-0">${rating.user.fullName}</h5>
                                <p class="mb-0 mx-3 text-secondary"><i><fmt:formatDate value="${rating.createdAtDate}"/></i></p>
                            </div>
                            <!-- Right side: rating -->
                            <div class="d-flex align-items-center">
                                <p class="mb-0 mr-2">Rating:</p>
                                <h4 class="mb-0 mx-1">${rating.rating}/10</h4>
                            </div>
                        </div>

                        <div class="mt-3">
                            <label for="comment" class="form-label">Comment</label>
                            <textarea id="comment" class="form-control" maxlength="1000" readonly>${rating.comment}</textarea>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>