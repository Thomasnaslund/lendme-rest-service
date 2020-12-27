CREATE TABLE loanItems(
  item_id INT REFERENCES items(id) ON UPDATE CASCADE,
  loan_id INT REFERENCES loans(id) ON UPDATE CASCADE ON DELETE CASCADE,
  amount numeric NOT NULL DEFAULT 1,
  CONSTRAINT items_loans_pkey PRIMARY KEY (item_id, loan_id)
);

ALTER TABLE loans DROP COLUMN item_id;
