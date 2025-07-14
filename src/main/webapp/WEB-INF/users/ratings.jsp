<main>
    <%@ include file="/WEB-INF/fragments/edit-profile-header.jspf" %>

    <section class="pt-0">
        <div class="container">
            <div class="row">
                <%@ include file="/WEB-INF/fragments/left-sidebar.jspf" %>

                <div class="col-lg-9">
                    <c:if test="${totalPages > 1}">
                        <div class="col d-flex justify-content-between align-items-center">
                            <%@include file="/WEB-INF/fragments/profile-rating-pagination.jspf"%>
                        </div>
                    </c:if>
                    <c:forEach items="${ratings}" var="rating">
                        <div class="border rounded p-3 mt-5 mb-5">
                            <div class="d-flex justify-content-between align-items-center">
                                <!-- Left side: movie info -->
                                <div class="d-flex align-items-center">
                                    <h5 class="mb-0"><a href="view-movies?id=${rating.movie_id}">${rating.movie.title}</a></h5>
                                    <p class="mb-0 mx-3 text-secondary"><i><fmt:formatDate value="${rating.createdAtDate}"/></i></p>
                                </div>
                                <!-- Right side: rating -->
                                <div class="d-flex align-items-center">
                                    <p class="mb-0 mr-2">Rating:</p>
                                    <h4 class="mb-0 mx-1">${rating.rating}/10</h4>
                                </div>
                            </div>

                            <div class="mt-3">
                                <label for="comment" class="form-label">Comment</label>
                                <textarea id="comment" class="form-control" maxlength="1000" readonly>${rating.comment}</textarea>
                            </div>
                        </div>
                    </c:forEach>
                    <c:if test="${totalPages > 1}">
                        <div class="col d-flex justify-content-between align-items-center">
                            <%@include file="/WEB-INF/fragments/profile-rating-pagination.jspf"%>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </section>
</main>