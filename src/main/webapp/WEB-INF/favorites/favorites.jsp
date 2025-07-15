<main>
    <%@ include file="/WEB-INF/fragments/edit-profile-header.jspf" %>

    <section class="pt-0">
        <div class="container">
            <div class="row">
                <%@ include file="/WEB-INF/fragments/left-sidebar.jspf" %>

                <div class="col-lg-9">
                    <div class="card bg-transparent border rounded-3">
                        <div class="card-header border-bottom">
                            <h3 class="card-header-title mb-0">Favorites</h3>
                        </div>
                        <div class="card-body">
                            <%@ include file="/WEB-INF/fragments/favorites.jspf"%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>