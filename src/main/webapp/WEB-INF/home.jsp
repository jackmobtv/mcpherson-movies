<div>
    <div class="row justify-content-center">
        <div class="col-12">
            <div class="custom-background text-center">
                <h1 class="text-white">Featured Movie</h1>
                <img src="${posterURL}" alt="poster" class="img-fluid"/>
                <h3 class="text-white mt-4">${title}</h3>
                <p class="text-white mx-auto" style="max-width: 600px;">${plot}</p> <!-- Added max-width and mx-auto -->
                <a class="btn btn-warning" href="${appURL}/view-movies?id=${id}">View Movie</a>
            </div>
        </div>
    </div>
</div>