<div class="row py-4 m-3">
    <c:forEach items="${actors}" var="actor">
        <div class="col-md-4 mb-4">
            <div class="card h-100">
                <div class="card-body text-center">
                    <h5 class="card-title">${actor.actor_name}</h5>
                    <a href="#" class="btn btn-secondary">View Movies</a>
                </div>
            </div>
        </div>
    </c:forEach>
</div>