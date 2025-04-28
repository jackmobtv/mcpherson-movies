<%
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
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Add Movie</h2>
        <a href="movies" class="btn btn-outline-primary float-right mx-2" role="button">Movie List</a>
    </div>

    <form class="row g-3" method="POST" action="add-movies">
        <div class="col-md-12">
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
            <input type="text" maxlength="4" class="form-control" id="release_year" name="release_year" value="${release_year == "0" ? "" : release_year}">
        </div>
        <div class="col-md-4">
            <label for="locationId" class="form-label">Location ID</label>
            <select class="form-select js-choice z-index-9" aria-label=".form-select-sm" id="locationId" name="locationId">
                <c:forEach items="${locations}" var="location">
                    <option value="${location.location_id}" ${location.location_id == locationId ? "selected" : ""}>${location.location_name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-4">
            <label for="formatId" class="form-label">Format ID</label>
            <select class="form-select js-choice z-index-9" aria-label=".form-select-sm" id="formatId" name="formatId">
                <c:forEach items="${formats}" var="format">
                    <option value="${format.format_id}" ${format.format_id == formatId ? "selected" : ""}>${format.format_name}</option>
                </c:forEach>
            </select>
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