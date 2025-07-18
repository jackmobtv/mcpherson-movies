<main class="py-5">
    <%@ include file="/WEB-INF/fragments/user-top-bar.jspf" %>
    <section class="pt-3">
        <div class="container">
            <div class="row">
                <%@ include file="/WEB-INF/fragments/user-left-sidebar.jspf" %>

                <div class="col-lg-9">
                    <div class="card bg-transparent border rounded-3">
                        <!-- Card header -->
                        <div class="card-header border-bottom">
                            <h3 class="card-header-title mb-0">Profile</h3>
                        </div>

                        <div class="card-body">
                            <c:choose>
                                <c:when test="${user eq null}">
                                    <h3>User Not Found</h3>
                                </c:when>
                                <c:otherwise>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div class="d-flex align-items-center">
                                            <c:choose>
                                                <c:when test="${empty image.encodedImage}">
                                                    <img class="cardImage mx-2" src="${appURL}/img/profile.svg" alt="Unset Profile Picture" height="90" width="90">
                                                </c:when>
                                                <c:otherwise>
                                                    <img class="cardImage mx-2" src="data:image/jpeg;base64,${image.encodedImage}" alt="${fn:escapeXml(image.fileName)}" height="90" width="90">
                                                </c:otherwise>
                                            </c:choose>
                                            <h3 class="mb-0">${fn:escapeXml(user.fullName)}</h3>
                                            <p class="mb-0 mx-3 text-secondary"><i><fmt:formatDate value="${user.dateofbirthDate}"/></i></p>
                                        </div>
                                        <div class="d-flex align-items-center">
                                            <h5 class="mb-0 mx-1">${fn:escapeXml(user.pronouns)}</h5>
                                        </div>
                                    </div>

                                    <div class="mt-3">
                                        <label for="description" class="form-label">Description</label>
                                        <textarea id="description" class="form-control" readonly>${fn:escapeXml(user.description)}</textarea>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>