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
                            <h4>${movie.title}</h4>
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
        </c:otherwise>
    </c:choose>
</div>