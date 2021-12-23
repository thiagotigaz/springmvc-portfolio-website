--
-- PostgreSQL database dump
--

-- Dumped from database version 11.9 (Debian 11.9-1.pgdg90+1)
-- Dumped by pg_dump version 11.9 (Debian 11.9-1.pgdg90+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: thiagosc
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO thiagosc;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: t_experience; Type: TABLE; Schema: public; Owner: thiagosc
--

CREATE TABLE public.t_experience (
    id integer NOT NULL,
    created_by_id bigint,
    created_date timestamp without time zone,
    last_modified_by_id bigint,
    last_modified_date timestamp without time zone,
    description character varying(2048) NOT NULL,
    enddate date,
    experiencetypeenum integer,
    startdate date NOT NULL,
    subtitle character varying(255) NOT NULL,
    title character varying(255) NOT NULL
);


ALTER TABLE public.t_experience OWNER TO thiagosc;

--
-- Name: t_file; Type: TABLE; Schema: public; Owner: thiagosc
--

CREATE TABLE public.t_file (
    id integer NOT NULL,
    created_by_id bigint,
    created_date timestamp without time zone,
    last_modified_by_id bigint,
    last_modified_date timestamp without time zone,
    content_type character varying(255),
    extension character varying(255),
    portfolio_id integer
);


ALTER TABLE public.t_file OWNER TO thiagosc;

--
-- Name: t_portfolio; Type: TABLE; Schema: public; Owner: thiagosc
--

CREATE TABLE public.t_portfolio (
    id integer NOT NULL,
    created_by_id bigint,
    created_date timestamp without time zone,
    last_modified_by_id bigint,
    last_modified_date timestamp without time zone,
    customer character varying(255),
    description character varying(2048),
    title character varying(255) NOT NULL,
    cover_image_id integer
);


ALTER TABLE public.t_portfolio OWNER TO thiagosc;

--
-- Name: t_portfolio_tags; Type: TABLE; Schema: public; Owner: thiagosc
--

CREATE TABLE public.t_portfolio_tags (
    t_portfolio_id integer NOT NULL,
    tags_id integer NOT NULL
);


ALTER TABLE public.t_portfolio_tags OWNER TO thiagosc;

--
-- Name: t_role; Type: TABLE; Schema: public; Owner: thiagosc
--

CREATE TABLE public.t_role (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.t_role OWNER TO thiagosc;

--
-- Name: t_tag; Type: TABLE; Schema: public; Owner: thiagosc
--

CREATE TABLE public.t_tag (
    id integer NOT NULL,
    name character varying(255)
);


ALTER TABLE public.t_tag OWNER TO thiagosc;

--
-- Name: t_user; Type: TABLE; Schema: public; Owner: thiagosc
--

CREATE TABLE public.t_user (
    id bigint NOT NULL,
    created_by_id bigint,
    created_date timestamp without time zone,
    last_modified_by_id bigint,
    last_modified_date timestamp without time zone,
    dob date,
    email character varying(255),
    name character varying(255),
    password character varying(255),
    username character varying(255)
);


ALTER TABLE public.t_user OWNER TO thiagosc;

--
-- Name: t_user_roles; Type: TABLE; Schema: public; Owner: thiagosc
--

CREATE TABLE public.t_user_roles (
    user_id bigint NOT NULL,
    roles_id bigint NOT NULL
);


ALTER TABLE public.t_user_roles OWNER TO thiagosc;

--
-- Data for Name: t_experience; Type: TABLE DATA; Schema: public; Owner: thiagosc
--

COPY public.t_experience (id, created_by_id, created_date, last_modified_by_id, last_modified_date, description, enddate, experiencetypeenum, startdate, subtitle, title) FROM stdin;
2	1	\N	1	2016-01-21 22:25:38.189	Specialization focused in android and cloud computing concepts being built with spring on top of AWS services.	2014-12-31	0	2014-01-01	Maryland & Vanderbilt University	Mobile Cloud Computing with Android Specialization
1	1	\N	1	2016-01-21 22:25:56.317	Computer Science bachelor degree focused in web applications.	2014-12-01	0	2008-06-01	Tecnologo em Sistemas para Internet	IFPB - INSTITUTO FEDERAL DE EDUCAÇÃO CIÊNCIA E TECNOLOGIA DA PARAIBA
3	1	\N	1	2016-01-21 22:25:43.975	Master of business administration focused in project management, PMBOK and PMI.	2017-03-01	0	2015-10-01	M.B.A in Project Management	FGV - Fundação Getulio Vargas
5	1	2016-01-22 00:34:29.419	1	2016-01-22 00:34:29.419	Participated in the Software Residency program promoted by CESAR and sponsored by Datacom which had the purpose of training professionals to work in the Datacom’s R&D department. Software Residency is a kind of training program that combines formal teaching and deep practice (based on Medical Residency). This one was focused on software development for Metro Ethernet Switches, using C, Linux, raw sockets, Pthreads, GNU Make, Git and Scrum. Performed the role of developer that worked with a team that implemented LLDP-MED (Link Layer Discovery Protocol-Media Endpoint Discovery) and designed and implemented LRP (Loop Recovery Protocol) [1], designing software architecture, specifying tasks, coding and revising code. Researched technical standards, such as TIAs, RFCs, IEEEs, and US Patents. [1] LRP is a proprietary protocol designed from scratch, in that residency, to detect loops in layer 2 networks.	2012-05-01	1	2011-11-01	CESAR	C DEVELOPER
6	1	2016-01-22 00:36:20.666	1	2016-01-22 00:36:20.666	Super Cloud is my personal brand where I had developed many projects as a freelancer. Those projects can be seen at the portifolio section of this website.	\N	1	2012-10-01	Super Cloud	Freelancer
7	1	2016-01-22 00:37:18.895	1	2016-01-22 00:37:18.895	Worked with Java Spring based applications in the pharmacy arena. The two major systems are http://suareceitadigital.com.br and http://lembredoremedio.com.br. The first one is responsible for scanning and keeping prescriptions available to the pharmacies. Those pictures are uploaded to amazon S3. The second system works sending notifications to patients with information about the medicine they're taking, like advising them when it's close to finish their treatment or when is the time to buy another box of the same medicine. All the systems in the company are Spring based applications, built with hibernate, maven and JSF on top of AWS stack. Before I leave the company I have started developing a new app of "Lembre do remedio" for Android. I'm still working for them as a freelancer, both in this app and as a consultant in other areas like servers infrastructure, AWS services, etc.	2015-04-01	1	2015-01-01	Pacto Mais	Java Engineer
8	1	2016-01-22 00:38:41.677	1	2016-01-22 00:38:41.677	Researching and developing an ERP in Nuclear Medicine Arena. In this project We are using Spring, JSF, Hibernate/Postgres, Maven and bootstrap 3/Jquery. For the app servers we started using wildfly on top of Linode VPS.\r\n\r\nI have just migrated the whole application to AWS. For that I had to redesign many components of the application to work with AWS services. The whole application configuration is now externalized in S3, so we just set environment variables when deploying to elastic beanstalk. This way it's much easier to deploy a instance for each client\r\nhaving common configurations shared and database credentials set as env variables.\r\n\r\nRight now I'm starting working in a continuous integration environment to make the stack even better.	\N	1	2015-04-01	Nuclearis	Java Engineer
\.


--
-- Data for Name: t_file; Type: TABLE DATA; Schema: public; Owner: thiagosc
--

COPY public.t_file (id, created_by_id, created_date, last_modified_by_id, last_modified_date, content_type, extension, portfolio_id) FROM stdin;
16	1	2016-01-24 21:17:27.436	1	2016-01-24 21:17:27.436	image/png	.png	15
19	1	2016-01-24 21:17:27.446	1	2016-01-24 21:17:27.446	image/png	.png	15
18	1	2016-01-24 21:17:27.436	1	2016-01-24 21:17:27.436	image/png	.png	15
17	1	2016-01-24 21:17:27.436	1	2016-01-24 21:17:27.436	image/png	.png	15
20	1	2016-01-24 21:17:27.448	1	2016-01-24 21:17:27.448	image/png	.png	15
22	1	2016-01-24 21:20:06.272	1	2016-01-24 21:20:06.272	image/jpeg	.jpg	21
30	1	2016-01-24 21:24:56.995	1	2016-01-24 21:24:56.995	image/png	.png	26
38	1	2016-01-24 21:25:04.391	1	2016-01-24 21:25:04.391	image/png	.png	26
23	1	2016-01-24 21:20:06.277	1	2016-01-24 21:20:06.277	image/jpeg	.jpg	21
28	1	2016-01-24 21:24:56.967	1	2016-01-24 21:24:56.967	image/png	.png	26
34	1	2016-01-24 21:25:03.882	1	2016-01-24 21:25:03.882	image/png	.png	26
61	1	2016-01-24 21:31:57.206	1	2016-01-24 21:31:57.206	image/png	.png	55
24	1	2016-01-24 21:20:06.287	1	2016-01-24 21:20:06.287	image/jpeg	.jpg	21
32	1	2016-01-24 21:24:57.003	1	2016-01-24 21:24:57.003	image/png	.png	26
27	1	2016-01-24 21:24:56.965	1	2016-01-24 21:24:56.965	image/png	.png	26
33	1	2016-01-24 21:25:03.194	1	2016-01-24 21:25:03.194	image/png	.png	26
29	1	2016-01-24 21:24:56.994	1	2016-01-24 21:24:56.994	image/png	.png	26
35	1	2016-01-24 21:25:03.899	1	2016-01-24 21:25:03.899	image/png	.png	26
39	1	2016-01-24 21:25:10.383	1	2016-01-24 21:25:10.383	image/png	.png	26
50	1	2016-01-24 21:29:21.099	1	2016-01-24 21:29:21.099	image/png	.png	48
31	1	2016-01-24 21:24:56.995	1	2016-01-24 21:24:56.995	image/png	.png	26
36	1	2016-01-24 21:25:03.965	1	2016-01-24 21:25:03.965	image/png	.png	26
60	1	2016-01-24 21:31:57.203	1	2016-01-24 21:31:57.203	image/png	.png	55
63	1	2016-01-24 21:33:50.073	1	2016-01-24 21:33:50.073	image/jpeg	.jpg	62
37	1	2016-01-24 21:25:04.37	1	2016-01-24 21:25:04.37	image/png	.png	26
59	1	2016-01-24 21:31:57.2	1	2016-01-24 21:31:57.2	image/png	.png	55
67	1	2016-01-24 21:33:50.092	1	2016-01-24 21:33:50.092	image/png	.png	62
40	1	2016-01-24 21:25:10.714	1	2016-01-24 21:25:10.714	image/png	.png	26
49	1	2016-01-24 21:29:21.097	1	2016-01-24 21:29:21.097	image/png	.png	48
58	1	2016-01-24 21:31:57.196	1	2016-01-24 21:31:57.196	image/png	.png	55
68	1	2016-01-24 21:33:50.095	1	2016-01-24 21:33:50.095	image/png	.png	62
56	1	2016-01-24 21:31:57.184	1	2016-01-24 21:31:57.184	image/png	.png	55
66	1	2016-01-24 21:33:50.092	1	2016-01-24 21:33:50.092	image/png	.png	62
57	1	2016-01-24 21:31:57.194	1	2016-01-24 21:31:57.194	image/png	.png	55
64	1	2016-01-24 21:33:50.079	1	2016-01-24 21:33:50.079	image/png	.png	62
65	1	2016-01-24 21:33:50.085	1	2016-01-24 21:33:50.085	image/png	.png	62
\.


--
-- Data for Name: t_portfolio; Type: TABLE DATA; Schema: public; Owner: thiagosc
--

COPY public.t_portfolio (id, created_by_id, created_date, last_modified_by_id, last_modified_date, customer, description, title, cover_image_id) FROM stdin;
55	1	2016-01-24 21:31:29.925	1	2016-01-24 21:31:59.176	Super Cloud		Abouts	59
15	1	\N	1	2016-01-24 21:19:23.962			Biblioteca Maria Eulália	19
21	1	\N	1	2016-01-24 21:20:47.714	Governo da Paraíba		Procurados PB	23
26	1	2016-01-24 21:22:40.869	1	2016-01-24 21:25:16.278	Adesivo2		Gestão	38
62	1	2016-01-24 21:33:16.058	1	2016-01-24 21:33:55.575	Super Cloud		This Website	68
48	1	2016-01-24 21:28:47.125	1	2016-01-24 21:29:22.362	Super Cloud		Photogift	50
\.


--
-- Data for Name: t_portfolio_tags; Type: TABLE DATA; Schema: public; Owner: thiagosc
--

COPY public.t_portfolio_tags (t_portfolio_id, tags_id) FROM stdin;
15	9
15	10
15	11
15	12
15	13
15	14
21	9
21	10
21	11
21	12
21	13
26	9
26	10
26	11
26	13
26	14
26	25
48	41
48	42
48	43
48	44
48	45
48	46
48	47
55	41
55	42
55	43
55	47
55	51
55	53
55	54
62	13
62	14
62	44
62	51
62	52
62	53
\.


--
-- Data for Name: t_role; Type: TABLE DATA; Schema: public; Owner: thiagosc
--

COPY public.t_role (id, name) FROM stdin;
\.


--
-- Data for Name: t_tag; Type: TABLE DATA; Schema: public; Owner: thiagosc
--

COPY public.t_tag (id, name) FROM stdin;
9	Primefaces
10	Jsf
11	Maven
12	Bootstrap
13	Jquery
14	Hibernate
25	Aws
41	Spring
42	Rest
43	Android
44	Postgres
45	Oauth2
46	Retrofit
47	Butterknife
51	Spring Boot
52	Thymeleaf
53	Gradle
54	Startup
\.


--
-- Data for Name: t_user; Type: TABLE DATA; Schema: public; Owner: thiagosc
--

COPY public.t_user (id, created_by_id, created_date, last_modified_by_id, last_modified_date, dob, email, name, password, username) FROM stdin;
1	1	\N	1	\N	1989-10-31	thiagotigaz@gmail.com	Thiago Lima	82ac6vww	thiago
\.


--
-- Data for Name: t_user_roles; Type: TABLE DATA; Schema: public; Owner: thiagosc
--

COPY public.t_user_roles (user_id, roles_id) FROM stdin;
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: thiagosc
--

SELECT pg_catalog.setval('public.hibernate_sequence', 68, true);


--
-- Name: t_experience t_experience_pkey; Type: CONSTRAINT; Schema: public; Owner: thiagosc
--

ALTER TABLE ONLY public.t_experience
    ADD CONSTRAINT t_experience_pkey PRIMARY KEY (id);


--
-- Name: t_file t_file_pkey; Type: CONSTRAINT; Schema: public; Owner: thiagosc
--

ALTER TABLE ONLY public.t_file
    ADD CONSTRAINT t_file_pkey PRIMARY KEY (id);


--
-- Name: t_portfolio t_portfolio_pkey; Type: CONSTRAINT; Schema: public; Owner: thiagosc
--

ALTER TABLE ONLY public.t_portfolio
    ADD CONSTRAINT t_portfolio_pkey PRIMARY KEY (id);


--
-- Name: t_role t_role_pkey; Type: CONSTRAINT; Schema: public; Owner: thiagosc
--

ALTER TABLE ONLY public.t_role
    ADD CONSTRAINT t_role_pkey PRIMARY KEY (id);


--
-- Name: t_tag t_tag_pkey; Type: CONSTRAINT; Schema: public; Owner: thiagosc
--

ALTER TABLE ONLY public.t_tag
    ADD CONSTRAINT t_tag_pkey PRIMARY KEY (id);


--
-- Name: t_user t_user_pkey; Type: CONSTRAINT; Schema: public; Owner: thiagosc
--

ALTER TABLE ONLY public.t_user
    ADD CONSTRAINT t_user_pkey PRIMARY KEY (id);


--
-- Name: t_portfolio_tags fk9cbc1u18nf5woi2umanvr6c60; Type: FK CONSTRAINT; Schema: public; Owner: thiagosc
--

ALTER TABLE ONLY public.t_portfolio_tags
    ADD CONSTRAINT fk9cbc1u18nf5woi2umanvr6c60 FOREIGN KEY (t_portfolio_id) REFERENCES public.t_portfolio(id);


--
-- Name: t_user_roles fkbh804p084jx9w7yfph5iv6yy0; Type: FK CONSTRAINT; Schema: public; Owner: thiagosc
--

ALTER TABLE ONLY public.t_user_roles
    ADD CONSTRAINT fkbh804p084jx9w7yfph5iv6yy0 FOREIGN KEY (roles_id) REFERENCES public.t_role(id);


--
-- Name: t_portfolio_tags fkd9ihhdxyhhpbn49i22slv4oj1; Type: FK CONSTRAINT; Schema: public; Owner: thiagosc
--

ALTER TABLE ONLY public.t_portfolio_tags
    ADD CONSTRAINT fkd9ihhdxyhhpbn49i22slv4oj1 FOREIGN KEY (tags_id) REFERENCES public.t_tag(id);


--
-- Name: t_user_roles fkkjoy1qmey3cmav2s1nuljk8pk; Type: FK CONSTRAINT; Schema: public; Owner: thiagosc
--

ALTER TABLE ONLY public.t_user_roles
    ADD CONSTRAINT fkkjoy1qmey3cmav2s1nuljk8pk FOREIGN KEY (user_id) REFERENCES public.t_user(id);


--
-- Name: t_portfolio fkklduuj3k6ktj4clisf23mfyv9; Type: FK CONSTRAINT; Schema: public; Owner: thiagosc
--

ALTER TABLE ONLY public.t_portfolio
    ADD CONSTRAINT fkklduuj3k6ktj4clisf23mfyv9 FOREIGN KEY (cover_image_id) REFERENCES public.t_file(id);


--
-- Name: t_file fkn1apb0aeek7y0hkb9vx4keuwt; Type: FK CONSTRAINT; Schema: public; Owner: thiagosc
--

ALTER TABLE ONLY public.t_file
    ADD CONSTRAINT fkn1apb0aeek7y0hkb9vx4keuwt FOREIGN KEY (portfolio_id) REFERENCES public.t_portfolio(id);


--
-- PostgreSQL database dump complete
--

