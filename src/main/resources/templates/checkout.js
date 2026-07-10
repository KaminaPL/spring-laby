// This is your test publishable API key.
const stripe = Stripe("pk_test_51ToOuoDs21VG5fgpZE2St4ficVyOPcgexH3m1WWlhp3T3fIYMjOKHsExxyG9VhYHxHusPyq05ng0MQQdTNpQiFUz00CfvlGyul");

initialize();

// Create a Checkout Session
async function initialize() {
  const fetchClientSecret = async () => {
    const response = await fetch("/create-checkout-session", {
      method: "POST",
    });
    const data = await response.json();
    if (data.error) {
      throw new Error(data.error);
    }
    return data.clientSecret;
  };

  const checkout = await stripe.createEmbeddedCheckoutPage({
    fetchClientSecret
  });

  // Mount Checkout
  checkout.mount('#checkout');
}