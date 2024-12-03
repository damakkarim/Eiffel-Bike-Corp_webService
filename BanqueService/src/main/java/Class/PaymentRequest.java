package Class;

public class PaymentRequest {
    private Long accountId;  // L'ID du compte pour lequel le paiement est effectué
    private double amount;   // Le montant à débiter

    // Constructeur par défaut
    public PaymentRequest() {}

    // Constructeur avec paramètres
    public PaymentRequest(Long accountId, double amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    // Getters et Setters
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
