# MiniBank

MiniBank is a Java 17+ command-line banking application. It demonstrates account
polymorphism, validation, checked banking exceptions, generic repositories,
thread-safe transaction processing, serialization, and NIO reporting.

## Build and test

From this directory, run:

```powershell
mvn test package
```

The JUnit tests cover deposits, insufficient-funds withdrawals, and concurrent
batch deposits. The runnable JAR is created at `target/minibank-1.0.0.jar`.

Run it with:

```powershell
java -jar target/minibank-1.0.0.jar
```

## Menu

The menu can open savings, current, and fixed-deposit accounts; deposit, withdraw,
or transfer funds; print an account statement; list accounts; process a batch of
deposit/withdraw commands; and generate an end-of-day report. Batch entries use
one command per line, in the form `DEPOSIT AC0001 500` or
`WITHDRAW AC0001 100`. Transfers are performed through the transfer menu option.

Account data is loaded from `data/accounts.ser` at startup and saved on exit.
Successful transactions are appended to `logs/transactions.log`. The report menu
scans that folder and writes `reports/end-of-day.txt`. Paths are relative to the
current working directory.
