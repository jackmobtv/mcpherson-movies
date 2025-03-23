<div class="row py-4 m-3">
    <c:forEach items="${actors}" var="actor">
        <div class="col-md-4 mb-4">
            <div class="card h-100">
                <div class="card-body text-center">
                    <h2 class="card-title">${actor.actor_name}</h2>
                    <div>
                        <c:if test="${not empty sessionScope.activeUser && sessionScope.activeUser.privileges eq 'Admin' && sessionScope.activeUser.status eq 'active'}">
                            <button type="button" class="btn btn-outline-warning open-modal" data-bs-toggle="modal" data-bs-target="#exampleModal" data-id="${actor.actor_id}" data-name="${actor.actor_name}">
                                Update Actor
                            </button>
                        </c:if>
                        <a href="${appURL}/view-actors?id=${actor.actor_id}" class="btn btn-outline-primary">View Movies</a>
                        <c:if test="${not empty sessionScope.activeUser && sessionScope.activeUser.privileges eq 'Admin' && sessionScope.activeUser.status eq 'active'}">
                            <form method="POST" action="delete-actors">
                                <input type="hidden" name="id" value="${actor.actor_id}">
                                <button type="submit" class="btn btn-outline-danger">Delete Actor</button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="actors" method="POST">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Enter New Actor Name</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                        <div class="form-floating">
                            <input type="text" class="form-control" id="name" name="name">
                            <label for="name">Actor Name</label>
                            <input type="hidden" id="id" name="id">
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