<div class="container col-xl-10 col-xxl-8 px-4 py-5">
    <div class="row align-items-center g-lg-5 py-5">
        <div class="col-md-10 mx-auto col-lg-5">
            <c:if test="${not empty userAddFail}">
                <div class="alert alert-danger mb-2">${userAddFail}</div>
            </c:if>
            <form method="POST" action="${appURL}/signup" class="p-4 p-md-5 border rounded-3 bg-body-tertiary">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control <c:if test="${not empty emailError}">is-invalid</c:if>" id="email" name="email" value="${email}" placeholder="name@example.com" onblur="checkEmail(event)">
                    <label for="email">Email address</label>
                    <div class="invalid-feedback" id="emailError"><c:if test="${not empty emailError}">${emailError}</c:if></div>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control <c:if test="${not empty password1Error}">is-invalid</c:if>" id="password" name="password" value="${password}" placeholder="Password">
                    <label for="password">Password</label>
                    <c:if test="${not empty password1Error}"><div class="invalid-feedback">${password1Error}</div></c:if>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" class="form-control <c:if test="${not empty password2Error}">is-invalid</c:if>" id="password-conf" name="password-conf" value="${passwordConf}" placeholder="Confirm Password">
                    <label for="password-conf">Confirm Password</label>
                    <c:if test="${not empty password2Error}"><div class="invalid-feedback">${password2Error}</div></c:if>
                </div>
                <div class="checkbox mb-3">
                    <input type="checkbox" value="agree" class="<c:if test="${not empty termsError}">is-invalid</c:if>" id="terms" name="terms" <c:if test="${terms eq 'agree'}">checked</c:if>>
                    <label for="terms">Agree to the <a href="${appURL}/terms">Terms of Service</a></label>
                    <c:if test="${not empty termsError}"><div class="invalid-feedback">${termsError}</div></c:if>
                </div>
                <div class="g-recaptcha" data-sitekey="6LfEWeAqAAAAAKdaJTHjDcWPtbh4EOo0i2Km1pHf"></div>
                <div style="color: indianred">${captchaError}</div>
                <br/>
                <button class="w-100 btn btn-lg btn-primary" type="submit">Sign up</button>
                <hr class="my-4">
                <small class="text-body-secondary">Already have an account? <a href="${appURL}/login">Log in</a></small>
            </form>
        </div>
    </div>
</div>