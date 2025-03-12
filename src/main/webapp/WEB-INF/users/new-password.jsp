<main>
    <section class="p-0 d-flex align-items-center position-relative overflow-hidden">
        <div class="container-fluid">
            <div class="row">
                <div class="col-12 col-lg-8 m-auto">
                    <div class="row my-5">
                        <div class="col-sm-10 col-xl-8 m-auto">
                            <h2>New password</h2>
                            <p class="lead mb-4">Please enter your new password.</p>

                            <c:if test="${not empty newPasswordFail}">
                                <div class="alert alert-danger mb-2" role="alert">
                                        ${newPasswordFail}
                                </div>
                            </c:if>

                            <!-- Form START -->
                            <form method="post" action="${appURL}/new-password">
                                <!-- Password -->
                                <div class="mb-4">
                                    <label for="inputPassword1" class="form-label">Password *</label>
                                    <div class="input-group input-group-lg">
                                        <span class="input-group-text bg-light rounded-start border-0 text-secondary px-3"><i class="fas fa-lock"></i></span>
                                        <input type="password" class="form-control <c:if test="${not empty password1Error }">is-invalid</c:if> border-2 rounded-end ps-1" placeholder="*********" id="inputPassword1" name="inputPassword1" value="${password1}">
                                        <c:if test="${not empty password1Error }"><div class="invalid-feedback">${password1Error}</div></c:if>
                                    </div>
                                </div>
                                <!-- Confirm Password -->
                                <div class="mb-4">
                                    <label for="inputPassword2" class="form-label">Confirm Password *</label>
                                    <div class="input-group input-group-lg">
                                        <span class="input-group-text bg-light rounded-start border-0 text-secondary px-3"><i class="fas fa-lock"></i></span>
                                        <input type="password" class="form-control <c:if test="${not empty password2Error }">is-invalid</c:if> border-2 rounded-end ps-1" placeholder="*********" id="inputPassword2" name="inputPassword2" value="${password2}">
                                        <c:if test="${not empty password2Error }"><div class="invalid-feedback">${password2Error}</div></c:if>
                                    </div>
                                </div>

                                <!-- Button -->
                                <div class="align-items-center mt-0">
                                    <div class="d-grid">
                                        <button class="btn btn-primary mb-0" type="submit">Submit</button>
                                    </div>
                                </div>
                            </form>
                            <!-- Form END -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>