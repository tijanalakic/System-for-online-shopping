# System-for-online-shopping
The system consists of three web applications: an application for managing user accounts, an application for managing items, and an application for viewing and purchasing items.
The system is implemented so that all communication between the client and the server is protected. When establishing communication, is required mutual authentication between
the client and the server. All digital certificates used are issued by a single CA body. SSO (Single Sign-On) mechanism is implemented. The entire system is protected by a 
simple WAF (Web Application Firewall) implemented on a web server. WAF scans traffic to a web server and misses only benign requests, and rejects potentially malicious ones.

******************
Implementirati jednostavan sistem za online kupovinu. Sistem se sastoji od tri web aplikacije:
aplikacija za upravljanje korisničkim nalozima, 
aplikacija za upravljanje artiklima
i aplikacija za pregled i kupovinu artikala. 
U skladu sa tim, potrebno je definisati tri nivoa pristupa:
• admin – može da pristupi svim aplikacijama,
• admin-artikli – može da pristupi aplikacijama za upravljanje i kupovinu artikala,
• kupac – može da pristupi samo aplikaciji za kupovinu artikala.
Sistem je potrebno implementirati tako da sva komunikacija između klijenta i servera bude zaštićena. Prilikom uspostavljanja komunikacije, potrebno je zahtijevati obostranu 
autentikaciju između klijenta i servera. Svi korišteni digitalni sertifikati treba da budu izdati od strane jednog CA tijela. U slučaju da korisnik pokuša pristupiti sistemu 
putem HTTP protokola, potrebno je preusmjeriti ga na HTTPS.
Potrebno je implementirati SSO (Single Sign On) mehanizam, tako da korisnici, koji imaju pristup više od jedne aplikacije, treba da se autentikuju prilikom pristupa samo prvoj 
aplikaciji.
Cijeli sistem treba da štiti jednostavan WAF (Web Application Firewall) kojeg treba implementirati na web serveru, ili na odvojenom proxy serveru. WAF treba da skenira saobraćaj 
prema web serveru i da propušta samo benigne zahtjeve, a odbija potencijalno maliciozne. Pri tome, potrebno je sve potencijalno maliciozne zahtjeve zapisivati u log datoteku,
u realnom vremenu. WAF treba da prati i analizira GET i POST zahtjeve, kao i podatke koji dolaze putem „kolačića“. HTTPS konekcije treba da se terminiraju na WAF-u.
Sve detalje zadatka koji nisu precizno specifikovani realizovati na proizvoljan način. Aplikacije treba da imaju minimalne funkcionalnosti koje su dovoljne za prezentaciju i
testiranje rada sistema za online kupovinu. Dozvoljena je upotreba proizvoljnog programskog jezika, kao i proizvoljne implementacije aplikativnog/web servera.
