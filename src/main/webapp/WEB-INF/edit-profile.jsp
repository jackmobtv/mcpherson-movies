<main>
    <%@ include file="/WEB-INF/fragments/edit-profile-header.jspf" %>

    <!-- Page content START -->
    <section class="pt-0">
        <div class="container">
            <div class="row">
                <%@ include file="/WEB-INF/fragments/left-sidebar.jspf" %>

                <!-- Main content START -->
                <div class="col-lg-9">
                    <!-- Edit profile START -->
                    <div class="card bg-transparent border rounded-3">
                        <!-- Card header -->
                        <div class="card-header border-bottom">
                            <h3 class="card-header-title mb-0">Edit Profile</h3>
                        </div>
                        <!-- Card body START -->
                        <div class="card-body">
                            <!-- Form -->
                            <form class="row g-4">

                                <!-- First name -->
                                <div class="col-md-6">
                                    <label class="form-label" for="firstName">First Name</label>
                                    <input class="form-control" type="text" id="firstName" name="firstName">
                                </div>

                                <!-- Last name -->
                                <div class="col-md-6">
                                    <label class="form-label" for="lastName">Last Name</label>
                                    <input type="text" class="form-control" id="lastName" name="lastName">
                                </div>

                                <!-- Email id -->
                                <div class="col-md-6">
                                    <label class="form-label" for="email">Email</label>
                                    <input class="form-control" type="text" id="email" name="email">
                                </div>

                                <!-- Phone number -->
                                <div class="col-md-6">
                                    <label class="form-label" for="phone">Phone number</label>
                                    <input type="text" class="form-control" id="phone" name="phone">
                                </div>

                                <!-- Select option -->
                                <div class="col-md-6">
                                    <!-- Language Preference -->
                                    <label class="form-label" for="language">Language</label>
                                    <select class="form-select js-choice z-index-9 bg-transparent" aria-label=".form-select-sm" id="language" name="language">
                                        <option value="en-US">English</option>
                                        <option value="es-MX">Spanish</option>
                                        <option value="fr-FR">French</option>
                                    </select>
                                </div>

                                <!-- Save button -->
                                <div class="d-sm-flex justify-content-end">
                                    <button type="submit" class="btn btn-primary mb-0">Save changes</button>
                                </div>
                            </form>
                        </div>
                        <!-- Card body END -->
                    </div>
                    <!-- Edit profile END -->
                </div>
                <!-- Main content END -->
            </div><!-- Row END -->
        </div>
    </section>
</main>