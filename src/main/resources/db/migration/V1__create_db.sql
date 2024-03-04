

create table Currencies
(
    id         int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    code    varchar,
    full_name varchar,
    sign     varchar
);



CREATE TABLE ExchangeRates (
   id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
   baseCurrencyId INT,
   targetCurrencyId INT,
    rate DECIMAL(6, 2),
    FOREIGN KEY (baseCurrencyId) REFERENCES Currencies(ID) ON DELETE CASCADE,
    FOREIGN KEY (targetCurrencyId) REFERENCES Currencies(ID) ON DELETE CASCADE,
    CONSTRAINT fk_base_currency FOREIGN KEY (baseCurrencyId) REFERENCES Currencies(ID),
    CONSTRAINT fk_target_currency FOREIGN KEY (targetCurrencyId) REFERENCES Currencies(ID)
);




