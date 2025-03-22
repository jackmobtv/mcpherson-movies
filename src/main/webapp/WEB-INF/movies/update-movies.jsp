<div class="container py-4">
    <h2>Update Movie</h2>
    <c:choose>
        <c:when test="${empty movie}">
            <p class="lead">Movie not found</p>
        </c:when>
        <c:otherwise>
            <form class="mb-3 mt-2" method="POST" action="delete-movies">
                <input type="hidden" name="id" value="${movie.movie_id}">
                <button class="btn btn-danger" type="submit">Delete Movie</button>
            </form>
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
        </c:otherwise>
    </c:choose>

    <div style="color:red;" class="mt-3">${error}</div>
    <div style="color:green;" class="mt-3">${success}</div>
</div>