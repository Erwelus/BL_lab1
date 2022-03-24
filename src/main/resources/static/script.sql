--Таблицы
CREATE TABLE Article
(
    ID          SERIAL PRIMARY KEY,
    ArticleName TEXT NOT NULL
);

CREATE TABLE Section
(
    ID         SERIAL PRIMARY KEY,
    Article_ID INTEGER REFERENCES article ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    SectionOrder INTEGER,
    NewestText TEXT DEFAULT NULL,
    LatestDate TIMESTAMP DEFAULT current_timestamp
);

CREATE TABLE Version
(
    ID         SERIAL PRIMARY KEY,
    Section_ID INTEGER REFERENCES section ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    SectionText TEXT,
    DateEdited TIMESTAMP NOT NULL DEFAULT current_timestamp,
    PersonEdited TEXT,
    IPEdited TEXT,
    Status TEXT DEFAULT 'Ожидает проверки'
);

--Индексы
CREATE INDEX Section_idx ON "section" USING hash("article_id");

CREATE INDEX Version_idx ON "version" USING hash("section_id");

--Функция вставки текста
CREATE OR REPLACE FUNCTION TextInsert() RETURNS TRIGGER AS
$$
begin
    IF (select NEW.DateEdited) > (select "latestdate" from "section" where id = NEW.Section_ID)
    then
update section SET newesttext = NEW.SectionText, latestdate = NEW.DateEdited where id = NEW.Section_ID;
raise debug 'Текст секции заменен на новый, дата изменения: %!', NEW.DateEdited;
end if;
return NEW;
end;
$$ LANGUAGE plpgsql;

--Триггер
DROP TRIGGER IF EXISTS "NewestTextInsert" ON "version";
CREATE TRIGGER NewestTextInsert
    AFTER INSERT
    ON "version"
    FOR EACH ROW
    EXECUTE PROCEDURE "textinsert"();