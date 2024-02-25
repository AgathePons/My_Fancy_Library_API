BEGIN;

DROP TABLE IF EXISTS
  "book",
  "author",
  "edition",
  "category",
  "user",
  "comment",
  "user_likes_book",
  "book_has_category";

CREATE TABLE IF NOT EXISTS "author" (
  "id" serial PRIMARY KEY,
  "firstname" TEXT NOT NULL,
  "lastname" TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS "edition" (
  "id" serial PRIMARY KEY,
  "name" TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS "category" (
  "id" serial PRIMARY KEY,
  "name" TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS "user" (
  "id" serial PRIMARY KEY,
  "firstname" TEXT NOT NULL,
  "lastname" TEXT NOT NULL,
  "mail" TEXT NOT NULL,
  "password" TEXT NOT NULL,
  "role" TEXT DEFAULT 'user'
);

CREATE TABLE IF NOT EXISTS "book" (
  "id" serial PRIMARY KEY,
  "title" TEXT NOT NULL,
  "abstract" TEXT NOT NULL,
  "year" INTEGER NOT NULL,
  "cover" TEXT NOT NULL,
  "author_id" INTEGER REFERENCES "author" ("id"),
  "edition_id" INTEGER REFERENCES "edition" ("id")
);

CREATE TABLE IF NOT EXISTS "comment" (
  "id" serial PRIMARY KEY,
  "text" TEXT NOT NULL,
  "user_id" INTEGER REFERENCES "user" ("id"),
  "book_id" INTEGER REFERENCES "book" ("id")
);

CREATE TABLE IF NOT EXISTS "user_likes_book" (
  "id" serial PRIMARY KEY,
  "user_id" INTEGER REFERENCES "user" ("id"),
  "book_id" INTEGER REFERENCES "book" ("id")
);

CREATE TABLE IF NOT EXISTS "book_has_category" (
  "id" serial PRIMARY KEY,
  "book_id" INTEGER REFERENCES "book" ("id"),
  "category_id" INTEGER REFERENCES "category" ("id")
);

COMMIT;