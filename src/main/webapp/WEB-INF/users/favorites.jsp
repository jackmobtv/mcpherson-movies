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
                            <div class="table-responsive small">
                                <table class="table table-striped table-sm">
                                    <thead>
                                    <tr>
                                        <th scope="col">Title</th>
                                        <th scope="col">Genre</th>
                                        <th scope="col">Year</th>
                                        <th scope="col">Location</th>
                                        <th scope="col">Format</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${favorites}" var="movies">
                                        <tr>
                                            <td class="align-middle"><a href="${appURL}/view-movies?id=${movies.movie_id}">${movies.title}</a></td>
                                            <td class="align-middle">${movies.genre}</td>
                                            <td class="align-middle">${movies.release_year == 0 ? "" : movies.release_year}</td>
                                            <td class="align-middle">${movies.location_name}</td>
                                            <td class="align-middle">${movies.format_name}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>