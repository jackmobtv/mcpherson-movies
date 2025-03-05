<%
    String movie_id = (String) request.getAttribute("movie_id");
    movie_id = movie_id == null ? "" : movie_id;
    String title = (String) request.getAttribute("title");
    title = title == null ? "" : title;
    String genre = (String) request.getAttribute("genre");
    genre = genre == null ? "" : genre;
    String sub_genre = (String) request.getAttribute("sub_genre");
    sub_genre = sub_genre == null ? "" : sub_genre;
    String release_year = (String) request.getAttribute("release_year");
    release_year = release_year == null || release_year.equals("0") ? "" : release_year;
    String locationId = (String) request.getAttribute("locationId");
    locationId = locationId == null ? "" : locationId;
    String formatId = (String) request.getAttribute("formatId");
    formatId = formatId == null ? "" : formatId;
    String actorName = (String) request.getAttribute("actorName");
    actorName = actorName == null ? "" : actorName;
    String error = (String) request.getAttribute("error");
    error = error == null ? "" : error;
    String success = (String) request.getAttribute("success");
    success = success == null ? "" : success;
%>
<div class="container py-4">
    <a href="movies" class="btn btn-warning mb-3" role="button">Movie List</a>
    <h2>Add Movie</h2>
    <form class="row g-3" method="POST" action="add-movies">
        <div class="col-md-4">
            <label for="movie_id" class="form-label">Movie ID</label>
            <input type="text" class="form-control" id="movie_id" name="movie_id" value="${movie_id}">
        </div>
        <div class="col-md-8">
            <label for="title" class="form-label">Title</label>
            <input type="text" class="form-control" id="title" name="title" value="${title}">
        </div>
        <div class="col-md-6">
            <label for="genre" class="form-label">Genre</label>
            <input type="text" class="form-control" id="genre" name="genre" value="${genre}">
        </div>
        <div class="col-md-6">
            <label for="sub_genre" class="form-label">Sub-Genre</label>
            <input type="text" class="form-control" id="sub_genre" name="sub_genre" value="${sub_genre}">
        </div>
        <div class="col-md-4">
            <label for="release_year" class="form-label">Year</label>
            <input type="text" maxlength="4" class="form-control" id="release_year" name="release_year" value="${release_year}">
        </div>
        <div class="col-md-4">
            <label for="locationId" class="form-label">Location ID</label>
            <input type="text" class="form-control" id="locationId" name="locationId" value="${locationId}">
        </div>
        <div class="col-md-4">
            <label for="formatId" class="form-label">Format ID</label>
            <input type="text" class="form-control" id="formatId" name="formatId" value="${formatId}">
        </div>
        <div class="col-md-4">
            <label for="actorName" class="form-label">Actor Name</label>
            <input type="text" class="form-control" id="actorName" name="actorName" value="${actorName}">
        </div>
        <div class="col-12">
            <button class="btn btn-secondary" type="submit">Submit form</button>
        </div>
    </form>
    <div style="color:red;" class="mt-3">${error}</div>
    <div style="color:green;" class="mt-3">${success}</div>
</div>