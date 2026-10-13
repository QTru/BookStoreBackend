USE bookstoredb;

ALTER TABLE accounts
    ADD COLUMN IF NOT EXISTS enabled BOOLEAN NOT NULL DEFAULT TRUE;

ALTER TABLE accounts
    MODIFY COLUMN enabled BOOLEAN NOT NULL DEFAULT TRUE;

-- Run only during initial setup, before any account is intentionally disabled.
UPDATE accounts
SET enabled = TRUE;