--
-- Database Table Creation
--
/*
DROP TABLE IF EXISTS Tag;
DROP TABLE IF EXISTS Tag_lemme;
DROP TABLE IF EXISTS Theme;
DROP TABLE IF EXISTS Document;
DROP TABLE IF EXISTS Type_spk;
DROP TABLE IF EXISTS Speaker;
DROP TABLE IF EXISTS Context;
DROP TABLE IF EXISTS Similarity;
DROP TABLE IF EXISTS Freq_tag_document;
DROP TABLE IF EXISTS Freq_tag_speaker_doc;
DROP TABLE IF EXISTS Freq_tag_theme;
DROP TABLE IF EXISTS Freq_tag_context;
DROP TABLE IF EXISTS Re_context_theme;
DROP TABLE IF EXISTS Re_doc_speaker;*/

/********* Entities ***********/

CREATE TABLE Tag ( -- original words
  id_tag BIGINT NOT NULL, -- AUTO_INCREMENT,
  nm_tag VARCHAR NOT NULL,
  PRIMARY KEY(id_tag)
);

CREATE UNIQUE INDEX TgOrgIndex1
	   ON Tag (id_tag);
CREATE UNIQUE INDEX TgOrgIndex2
	   ON Tag (nm_tag);


CREATE TABLE Tag_lemme (
  id_tag BIGINT NOT NULL, -- AUTO_INCREMENT,
  nm_tag VARCHAR NOT NULL,
  PRIMARY KEY(id_tag)
);

CREATE UNIQUE INDEX TgLemIndex1
	   ON Tag (id_tag);
CREATE UNIQUE INDEX TgLemIndex2
	   ON Tag (nm_tag);


	   
CREATE TABLE Theme (
  id_theme INTEGER UNSIGNED NOT NULL, -- AUTO_INCREMENT,
  nm_theme VARCHAR(20) NOT NULL,
  PRIMARY KEY(id_theme)
);

CREATE UNIQUE INDEX ThmIndex1
	   ON Theme (id_theme);
CREATE UNIQUE INDEX ThmIndex2
	   ON Theme (nm_theme);

	   
CREATE TABLE Document (
  id_doc INTEGER UNSIGNED NOT NULL, -- AUTO_INCREMENT,
  nm_doc VARCHAR NOT NULL,
  date_doc DATE NOT NULL,
  url VARCHAR(200), 
  PRIMARY KEY(id_doc)
);

CREATE UNIQUE INDEX DocIndex1
	   ON Document (id_doc);
CREATE UNIQUE INDEX DocIndex2
	   ON Document (date_doc);

	   
CREATE TABLE Type_spk (
  id_type INTEGER UNSIGNED NOT NULL, -- AUTO_INCREMENT,
  nm_type VARCHAR(20) NOT NULL,
  PRIMARY KEY(id_type)
);

CREATE UNIQUE INDEX TypspkIndex1
	   ON Type_spk (id_type);
CREATE UNIQUE INDEX TypspkIndex2
	   ON Type_spk (nm_type);


CREATE TABLE Speaker (
  id_spk INTEGER UNSIGNED NOT NULL,-- AUTO_INCREMENT,
  id_type INTEGER UNSIGNED NOT NULL,
  lname VARCHAR(20) NOT NULL,
  fname VARCHAR(20) NOT NULL,
  gender VARCHAR(1) NOT NULL,	 -- Male/Female
  birthyear INTEGER NULL,
  dscp VARCHAR(100) NULL, -- description, fonction
  party VARCHAR(45) NULL,
  pol_color VARCHAR(10) NULL, 
  url_wiki VARCHAR(100) NULL,
  PRIMARY KEY(id_spk),
  FOREIGN KEY(id_type)
    REFERENCES type_spk(id_type)
      ON DELETE SET NULL
      ON UPDATE CASCADE
);

CREATE UNIQUE INDEX SpkIndex1
	   ON Speaker (id_spk);
CREATE UNIQUE INDEX SpkIndex2
	   ON Speaker (lname);

	   
CREATE TABLE Context (
  id_context INTEGER UNSIGNED NOT NULL,-- AUTO_INCREMENT,
  id_doc INTEGER UNSIGNED NOT NULL,
  id_spk INTEGER UNSIGNED NOT NULL,
  Q_A VARCHAR(1) NOT NULL,  -- Question/Answer
  text VARCHAR NOT NULL,
  Weight INTEGER UNSIGNED,
  PRIMARY KEY(id_context),
  FOREIGN KEY(id_doc)
    REFERENCES Document(id_doc)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(id_spk)
    REFERENCES Speaker(id_spk)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE UNIQUE INDEX CtxIndex1
	   ON Context (id_context, id_doc, id_spk);

	   
/******** Relations - Similarity ********/

CREATE TABLE Distance_org(
  id_spk_in_doc1 INTEGER UNSIGNED NOT NULL,
  id_spk_in_doc2 INTEGER UNSIGNED NOT NULL,
  dist DOUBLE NOT NULL,
  PRIMARY KEY(id_spk_in_doc1, id_spk_in_doc2),
  FOREIGN KEY(id_spk_in_doc1)
    REFERENCES Re_doc_speaker(id_spk_in_doc)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(id_spk_in_doc2)
    REFERENCES Re_doc_speaker(id_spk_in_doc)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE UNIQUE INDEX distorgIndex1
	   ON Distance_org (id_spk_in_doc1, id_spk_in_doc2);
	   
	   
CREATE TABLE Distance_lem(
  id_spk_in_doc1 INTEGER UNSIGNED NOT NULL,
  id_spk_in_doc2 INTEGER UNSIGNED NOT NULL,
  dist DOUBLE NOT NULL,
  PRIMARY KEY(id_spk_in_doc1, id_spk_in_doc2),
  FOREIGN KEY(id_spk_in_doc1)
    REFERENCES Re_doc_speaker(id_spk_in_doc)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(id_spk_in_doc2)
    REFERENCES Re_doc_speaker(id_spk_in_doc)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE UNIQUE INDEX distlemIndex1
	   ON Distance_lem (id_spk_in_doc1, id_spk_in_doc2);
   
CREATE TABLE Weight_context( ---------add a column ALTER !!!
  id_context INTEGER UNSIGNED NOT NULL, 
  weight DOUBLE NOT NULL, 
  PRIMARY KEY(id_context),
  FOREIGN KEY(id_context)
    REFERENCES Re_doc_speaker(id_spk_in_doc)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

/******** Relations - Frequency ********/

CREATE TABLE Freq_tag_document (
  id_tag BIGINT NOT NULL,
  id_doc INTEGER UNSIGNED NOT NULL,
  freq DOUBLE NOT NULL,
  PRIMARY KEY(id_tag, id_doc),
  FOREIGN KEY(id_tag)
    REFERENCES Tag(id_tag)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(id_doc)
    REFERENCES Document(id_doc)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE UNIQUE INDEX ftgdcIndex1
	   ON Freq_tag_document (id_tag, id_doc);
	   
	   
CREATE TABLE Freq_tag_theme (
  id_tag BIGINT NOT NULL,
  id_theme INTEGER UNSIGNED NOT NULL,
  freq DOUBLE NOT NULL,
  PRIMARY KEY(id_tag, id_theme),
  FOREIGN KEY(id_tag)
    REFERENCES Tag(id_tag)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(id_theme)
    REFERENCES Theme(id_theme)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE UNIQUE INDEX ftgthmIndex1
	   ON Freq_tag_theme (id_tag, id_theme);
	   
	   
---- tag-speaker-doc ----

CREATE TABLE Freq_tag_org_speaker_doc (
  id_tag BIGINT NOT NULL,
  id_comb INTEGER UNSIGNED NOT NULL,	-- combinaison
  freq DOUBLE NOT NULL,
  PRIMARY KEY(id_tag, id_comb),
  FOREIGN KEY(id_tag)
    REFERENCES Tag(id_tag)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE UNIQUE INDEX ftgspkOrgIndex1
	   ON Freq_tag_org_speaker_doc (id_tag, id_comb);


CREATE TABLE Freq_tag_lem_speaker_doc (
  id_tag BIGINT NOT NULL,
  id_comb INTEGER UNSIGNED NOT NULL,	-- combinaison
  freq DOUBLE NOT NULL,
  PRIMARY KEY(id_tag, id_comb),
  FOREIGN KEY(id_tag)
    REFERENCES Tag(id_tag)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE UNIQUE INDEX ftgspkLemIndex1
	   ON Freq_tag_lem_speaker_doc (id_tag, id_comb);


/******** Relations - Dependency ********/

CREATE TABLE Re_org_lem (
  id_org BIGINT UNSIGNED NOT NULL,
  id_lem BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY(id_org),
  FOREIGN KEY(id_lem)
  	REFERENCES Tag_lemme(id_tag)
  	  ON DELETE CASCADE
  	  ON UPDATE CASCADE
);

CREATE UNIQUE INDEX rorglemIndex1
      ON Re_org_lem (id_org, id_lem);

CREATE TABLE Re_context_theme (
  id_context INTEGER UNSIGNED NOT NULL,
  id_theme INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id_context, id_theme),
  FOREIGN KEY(id_context)
    REFERENCES Context(id_context)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(id_theme)
    REFERENCES Theme(id_theme)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE UNIQUE INDEX rctxthmIndex1
	   ON Re_context_theme (id_context, id_theme);
	   
	   
CREATE TABLE Re_doc_speaker (
  id_spk_in_doc INTEGER UNSIGNED NOT NULL,
  id_doc INTEGER UNSIGNED NOT NULL,
  id_spk INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id_spk_in_doc),
  FOREIGN KEY(id_doc)
    REFERENCES Document(id_doc)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(id_spk)
    REFERENCES Speaker(id_spk)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE UNIQUE INDEX rdcspkIndex1
	   ON Re_doc_speaker (id_spk_in_doc, id_doc, id_spk);	   


CREATE TABLE Re_tag_context (
  id_tag BIGINT NOT NULL,
  id_context INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(id_tag, id_context),
  FOREIGN KEY(id_tag)
    REFERENCES Tag(id_tag)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(id_context)
    REFERENCES Context(id_context)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE UNIQUE INDEX rtgctxIndex1
	   ON Re_tag_context (id_tag, id_context);

