<main class="py-5">
    <section class="pt-0">
        <div class="container">
            <div class="row">
                <%@ include file="/WEB-INF/fragments/user-left-sidebar.jspf" %>

                <div class="col-lg-9">
                    <div class="card bg-transparent border rounded-3">
                        <!-- Card header -->
                        <div class="card-header border-bottom">
                            <h3 class="card-header-title mb-0">Favorites</h3>
                        </div>

                        <div class="card-body">
                            <c:choose>
                                <c:when test="${user eq null}">
                                    <h3>User Not Found</h3>
                                </c:when>
                                <c:otherwise>
                                    <%@ include file="/WEB-INF/fragments/favorites.jspf" %>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>