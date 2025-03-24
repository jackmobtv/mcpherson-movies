<div class="container py-4">
    <h2>Update Movie</h2>
    <c:choose>
        <c:when test="${empty movie}">
            <p class="lead">Movie not found</p>
        </c:when>
        <c:otherwise>
            <div>
                <a class="btn btn-primary text-right" href="${appURL}/view-movies?id=${movie.movie_id}">View Movie</a>
                <form class="mb-3 mt-2" method="POST" action="delete-movies" onsubmit="return confirm('Are You Sure?');">
                    <input type="hidden" name="id" value="${movie.movie_id}">
                    <button class="btn btn-danger" type="submit">Delete Movie</button>
                </form>
            </div>
            <form class="row g-3" method="POST" action="update-movies">
                <div class="col-md-4">
                    <label for="movie_id" class="form-label">Movie ID</label>
                    <input readonly type="text" class="form-control" id="movie_id" name="movie_id" value="${movie.movie_id}">
                </div>
                <div class="col-md-8">
                    <label for="title" class="form-label">Title</label>
                    <input type="text" class="form-control" id="title" name="title" value="${movie.title}">
                </div>
                <div class="col-md-6">
                    <label for="genre" class="form-label">Genre</label>
                    <input type="text" class="form-control" id="genre" name="genre" value="${movie.genre}">
                </div>
                <div class="col-md-6">
                    <label for="sub_genre" class="form-label">Sub-Genre</label>
                    <input type="text" class="form-control" id="sub_genre" name="sub_genre" value="${movie.sub_genre}">
                </div>
                <div class="col-md-4">
                    <label for="release_year" class="form-label">Year</label>
                    <input type="text" maxlength="4" class="form-control" id="release_year" name="release_year" value="${movie.release_year}">
                </div>
                <div class="col-md-4">
                    <label for="locationId" class="form-label">Location ID</label>
                    <input type="text" class="form-control" id="locationId" name="locationId" value="${movie.location_id}">
                </div>
                <div class="col-md-4">
                    <label for="formatId" class="form-label">Format ID</label>
                    <input readonly type="text" class="form-control" id="formatId" name="formatId" value="${movie.format_id}">
                </div>
                <div class="col-12">
                    <button class="btn btn-secondary" type="submit">Submit form</button>
                </div>
            </form>

            <div style="color:red;" class="mt-3">${error}</div>
            <div style="color:green;" class="mt-3">${success}</div>

            <button type="button" class="btn btn-primary mt-5 mb-4" data-bs-toggle="modal" data-bs-target="#exampleModal">
                Add Actor
            </button>

            <div class="table-responsive small">
                <table class="table table-striped table-sm">
                    <thead>
                    <tr>
                        <th scope="col">Actor Name</th>
                        <th scope="col" class="col-3"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${actors}" var="actor">
                        <tr>
                            <td class="align-middle"><h5>${actor.actor_name}</h5></td>
                            <td>
                                <a href="${appURL}/view-actors?id=${actor.actor_id}" class="btn btn-outline-primary">View Movies</a>
                                <form action="delete-movie-actors" method="POST" onsubmit="return confirm('Are You Sure?');">
                                    <input type="hidden" value="${actor.actor_id}" name="actor_id">
                                    <input type="hidden" value="${movie.movie_id}" name="movie_id">
                                    <button type="submit" class="btn btn-outline-danger">Delete Actor</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="add-actors" method="POST">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Enter New Actor Name</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="name" name="name">
                        <label for="name">Actor Name</label>
                        <input type="hidden" name="id" value="${movie.movie_id}">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save changes</button>
                </div>
            </form>
        </div>
    </div>
</div>