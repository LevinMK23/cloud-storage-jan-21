package stream;

import java.util.Optional;

public class BalanceService {

    Optional<Balance> getClientBalance(int clientId) {
        return Optional.of(new Balance(clientId, 100500));
    }

}
