--
-- Database Value Insertion
--

/****************** Document **************/

INSERT OR REPLACE INTO Document (id_doc, date_doc, nm_doc, url)
       VALUES ( 1, '1981-05-05','Débat du 5 mai 1981 entre Valéry Giscard d’Estaing et François Mitterrand', 'http://discours.vie-publique.fr/notices/817005300.html'
);

INSERT OR REPLACE INTO Document (id_doc, date_doc, nm_doc, url)
       VALUES ( 2, '1988-04-28', 'Débat du 28 avril 1988 entre François Mitterrand et Jacques Chirac', 'http://discours.vie-publique.fr/notices/887013300.html'
);

INSERT OR REPLACE INTO Document (id_doc, date_doc, nm_doc, url)
       VALUES ( 3, '1995-05-02', 'Débat du 2 mai 1995 entre Jacques Chirac et Lionel Jospin', 'http://discours.vie-publique.fr/notices/953187300.html'
);

INSERT OR REPLACE INTO Document (id_doc, date_doc, nm_doc, url)
       VALUES ( 4, '2007-05-02', 'Débat du 2 mai 2007 entre Ségolène Royal et Nicolas Sarkozy', 'http://discours.vie-publique.fr/notices/073001650.html'
);

/**************** Speaker **************/

-------------DOC 1-------------
INSERT OR REPLACE INTO Speaker (id_spk,		id_type,		lname,	fname,	gender,			birthyear, dscp, party, 	pol_color,   url_wiki)
	   VALUES (1, 1, 'GISCARD D''ESTAING','Valéry',	'M',	1926, NULL,		'party1',  'gauche/droite', 'http://fr.wikipedia.org/wiki/Valéry_Giscard_d''Estaing'
);
	   
INSERT OR REPLACE INTO Speaker
	   VALUES (2, 1, 'MITTERRAND', 'François', 'M', 1916, '21e président de la République',	NULL, NULL, 'fr.wikipedia.org/wiki/François_Mitterrand'
);

INSERT OR REPLACE INTO Speaker
	   VALUES (3, 2, 'COTTA', 'Michèle', 'F',	NULL, 'journaliste à RTL', NULL, NULL, NULL
);

INSERT OR REPLACE INTO Speaker
	   VALUES (4, 2, 'BOISSONNAT', 'Jean', 'M',  NULL,  'journaliste à L''Expansion et chroniqueur à Europe I', NULL, NULL, NULL
);

------------DOC 2-------------(Mitterrand, Chirac)
INSERT OR REPLACE INTO Speaker
	   VALUES (5, 1, 'CHIRAC', 'Jacques', 'M', 1932, 'Premier ministre, 22e président de la République', 'RPR', NULL, 'fr.wikipedia.org/wiki/Jacques_Chirac‎'
);

------------DOC 3-------------(Chirac, Jospin)
INSERT OR REPLACE INTO Speaker
	   VALUES (6, 1, 'JOSPIN', 'Lionel', 'M', 1937, 'membre du bureau national', 'PS', NULL, 'fr.wikipedia.org/wiki/Lionel_Jospin‎'
);

INSERT OR REPLACE INTO Speaker
	   VALUES (7, 2, 'DUHAMEL', 'Alain', 'M', NULL, NULL, NULL, NULL, NULL
);

INSERT OR REPLACE INTO Speaker
	   VALUES (8, 2, 'DURAND',  'Guillaume', 'M', NULL, NULL, NULL, NULL, NULL
);

------------DOC 4-------------
INSERT OR REPLACE INTO Speaker
	   VALUES (9, 1, 'ROYAL', 'Ségolène', 'F', 1953, NULL, 'PS', NULL, 'fr.wikipedia.org/wiki/Ségolène_Royal'
);

INSERT OR REPLACE INTO Speaker
	   VALUES (10, 1, 'SARKOZY', 'Nicolas', 'M', 1955, 'président de UMP', 'UMP', NULL, 'fr.wikipedia.org/wiki/Nicolas_Sarkozy'
);

INSERT OR REPLACE INTO Speaker
	   VALUES (11, 2, 'CHABOT',  'Arlette', 'F', NULL, NULL, NULL, NULL, NULL
);

INSERT OR REPLACE INTO Speaker
	   VALUES (12, 2, 'POIVRE D''ARVOR', 'Patrick', 'M', NULL, NULL, NULL, NULL, NULL
); 

----DOC2---
INSERT OR REPLACE INTO Speaker
	   VALUES (13, 2, 'VANNIER', 'x', 'M', NULL, NULL, NULL, NULL, NULL
); 

/**************** Type_spk  **************/

INSERT OR REPLACE INTO Type_spk (id_type,		nm_type)
	   VALUES 	  	   			(1,				'candidate'
);
INSERT OR REPLACE INTO Type_spk (id_type,		nm_type)
	   VALUES	  	   			(2, 			'journalist'
);


/**************** Re_doc_speaker  **************/

INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (1,				1,		1
);
INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (2,				1,		2
);
INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (3,				1,		3
);
INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (4,				1,		4
);		
INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (5,				2,		2
);		
INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (6,				2,		5
);		
INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (7,				2,		3
);		
INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (8,				3,		5
);		
INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (9,				3,		6
);		
INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (10,				3,		7
);
INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (11,				3,		8
);
INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (12,				4,		9
);
INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (13,				4,		10
);
INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (14,				4,		11
);
INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (15,				4,		12
);		
INSERT OR REPLACE INTO Re_doc_speaker (id_spk_in_doc, id_doc,	id_spk)
		VALUES						  (16,				2,		13
);	
		
/********** Combinasion Personne **********
 *  Doc	  |	 Person	 |	Bit	
 *------- + -------- + --------
 *	1981	Giscard 	0
 	1981	Mitterrand	1
	1988	Chirac		2
	1988	Mitterrand	3
	1995	Chirac		4
	1995	Jospin		5
	2007	Royal		6
	2007	Sarkozy		7
  ------- + -------- + ---------
 * 
 ****************************************/


/********* Themes ****************/

INSERT OR REPLACE INTO Theme (id_theme,		nm_theme)
	   VALUES 	  	   		 (1,			'ECONOMIE'
);

INSERT OR REPLACE INTO Theme (id_theme,		nm_theme)
	   VALUES 	  	   		 (2,			'DEPENSE'
);

INSERT OR REPLACE INTO Theme (id_theme,		nm_theme)
	   VALUES 	  	   		 (3,			'EDUCATION'
);

INSERT OR REPLACE INTO Theme (id_theme,		nm_theme)
	   VALUES 	  	   		 (4,			'EUROPE'
);

INSERT OR REPLACE INTO Theme (id_theme,		nm_theme)
	   VALUES 	  	   		 (5,			'IMMIGRATION'
);

INSERT OR REPLACE INTO Theme (id_theme,		nm_theme)
	   VALUES 	  	   		 (6,			'NUCLEAIRE'
);

INSERT OR REPLACE INTO Theme (id_theme,		nm_theme)
	   VALUES 	  	   		 (7,			'INSTITUTION'
);

INSERT OR REPLACE INTO Theme (id_theme,		nm_theme)
	   VALUES 	  	   		 (8,			'POLITIQUE'
);

		

