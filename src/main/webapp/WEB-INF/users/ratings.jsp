<main>
    <%@ include file="/WEB-INF/fragments/edit-profile-header.jspf" %>

    <section class="pt-0">
        <div class="container">
            <div class="row">
                <%@ include file="/WEB-INF/fragments/left-sidebar.jspf" %>

                <div class="col-lg-9">
                    <div class="card bg-transparent border rounded-3">
                        <!-- Card header -->
                        <div class="card-header border-bottom">
                            <h3 class="card-header-title mb-0">Ratings</h3>
                        </div>

                        <div class="card-body">
                            <c:if test="${totalPages > 1}">
                                <div class="col d-flex justify-content-between align-items-center">
                                    <%@include file="/WEB-INF/fragments/profile-rating-pagination.jspf"%>
                                </div>
                            </c:if>
                            <%@include file="/WEB-INF/fragments/ratings.jspf"%>
                            <c:if test="${totalPages > 1}">
                                <div class="col d-flex justify-content-between align-items-center">
                                    <%@include file="/WEB-INF/fragments/profile-rating-pagination.jspf"%>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>