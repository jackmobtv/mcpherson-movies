<div class="container">
    <main>
        <div class="py-5 text-center">
            <h1 class="h2">Checkout</h1>
        </div>

        <div class="d-flex justify-content-center align-items-center mb-3">
            <div class="col-md-7">
                <form action="subscribe" method="POST">
                    <div class="row gy-3">
                        <div class="col-md-6">
                            <label for="cc-number" class="form-label">Credit card number</label>
                            <input type="text" class="form-control" id="cc-number" name="cc-number" placeholder="">
                            <div class="invalid-feedback">
                                Credit card number is required
                            </div>
                        </div>

                        <div class="col-md-3">
                            <label for="cc-expiration" class="form-label">Expiration</label>
                            <input type="text" class="form-control" id="cc-expiration" name="cc-expiration" placeholder="">
                            <div class="invalid-feedback">
                                Expiration date required
                            </div>
                        </div>

                        <div class="col-md-3">
                            <label for="cc-cvv" class="form-label">CVV</label>
                            <input type="text" class="form-control" id="cc-cvv" name="cc-cvv" placeholder="">
                            <div class="invalid-feedback">
                                Security code required
                            </div>
                        </div>
                    </div>

                    <hr class="my-4">

                    <button class="w-100 btn btn-primary btn-lg" type="submit">Submit</button>
                </form>
            </div>
        </div>
    </main>
</div>