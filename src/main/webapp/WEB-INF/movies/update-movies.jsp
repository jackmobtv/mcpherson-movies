<div class="container py-4">
    <h2>Update Movie</h2>
    <c:choose>
        <c:when test="${empty movie}">
            <p class="lead">Movie not found</p>
        </c:when>
        <c:otherwise>
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

<!--
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz4fnFO9gybD26Z1dI8b4G9RxgUoCwxC2fxnRvbGp8AKgW7l+wlU0O8VfC" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-OR+ZlZtCwLDH8LZzSnN3v0C3osHr1fE5vE/pUszUXR7Xo0t5iG4u5Rp/RI56cTV1" crossorigin="anonymous"></script>
-->
</body>
</html>
