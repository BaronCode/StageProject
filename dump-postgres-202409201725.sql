PGDMP                           |           postgres    12.20    12.20 +    7           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            8           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            9           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            :           1262    13318    postgres    DATABASE     �   CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Italian_Italy.1252' LC_CTYPE = 'Italian_Italy.1252';
    DROP DATABASE postgres;
                postgres    false            ;           0    0    DATABASE postgres    COMMENT     N   COMMENT ON DATABASE postgres IS 'default administrative connection database';
                   postgres    false    2874                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                postgres    false            <           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   postgres    false    4            �            1259    16403 
   activities    TABLE     ?  CREATE TABLE public.activities (
    name character varying NOT NULL,
    priority character varying NOT NULL,
    creator_name character varying NOT NULL,
    id integer NOT NULL,
    description character varying DEFAULT ''::character varying NOT NULL,
    delegate character varying,
    status character varying
);
    DROP TABLE public.activities;
       public         heap    postgres    false    4            �            1259    24722    activities_id_seq    SEQUENCE     �   CREATE SEQUENCE public.activities_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.activities_id_seq;
       public          postgres    false    4    204            =           0    0    activities_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.activities_id_seq OWNED BY public.activities.id;
          public          postgres    false    208            �            1259    16411    inter    TABLE     x   CREATE TABLE public.inter (
    "row" integer NOT NULL,
    name character varying NOT NULL,
    id integer NOT NULL
);
    DROP TABLE public.inter;
       public         heap    postgres    false    4            �            1259    24589    inter_row_seq    SEQUENCE     �   CREATE SEQUENCE public.inter_row_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.inter_row_seq;
       public          postgres    false    205    4            >           0    0    inter_row_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.inter_row_seq OWNED BY public.inter."row";
          public          postgres    false    206            �            1259    24787    messages    TABLE     �   CREATE TABLE public.messages (
    activity integer NOT NULL,
    author character varying NOT NULL,
    body character varying NOT NULL,
    "timestamp" timestamp without time zone NOT NULL,
    id integer NOT NULL
);
    DROP TABLE public.messages;
       public         heap    postgres    false    4            �            1259    24815    messages_id_seq    SEQUENCE     �   CREATE SEQUENCE public.messages_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.messages_id_seq;
       public          postgres    false    211    4            ?           0    0    messages_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.messages_id_seq OWNED BY public.messages.id;
          public          postgres    false    212            �            1259    24744    notifications    TABLE     �   CREATE TABLE public.notifications (
    "timestamp" timestamp without time zone NOT NULL,
    status boolean NOT NULL,
    id integer NOT NULL,
    title character varying NOT NULL,
    body character varying NOT NULL
);
 !   DROP TABLE public.notifications;
       public         heap    postgres    false    4            �            1259    24759    notifications_id_seq    SEQUENCE     �   CREATE SEQUENCE public.notifications_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.notifications_id_seq;
       public          postgres    false    209    4            @           0    0    notifications_id_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.notifications_id_seq OWNED BY public.notifications.id;
          public          postgres    false    210            �            1259    24621 
   user_roles    TABLE     q   CREATE TABLE public.user_roles (
    role character varying NOT NULL,
    username character varying NOT NULL
);
    DROP TABLE public.user_roles;
       public         heap    postgres    false    4            �            1259    16395    users    TABLE     �   CREATE TABLE public.users (
    name character varying NOT NULL,
    mail character varying NOT NULL,
    psw character varying NOT NULL
);
    DROP TABLE public.users;
       public         heap    postgres    false    4            �
           2604    24724    activities id    DEFAULT     n   ALTER TABLE ONLY public.activities ALTER COLUMN id SET DEFAULT nextval('public.activities_id_seq'::regclass);
 <   ALTER TABLE public.activities ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    208    204            �
           2604    24591 	   inter row    DEFAULT     h   ALTER TABLE ONLY public.inter ALTER COLUMN "row" SET DEFAULT nextval('public.inter_row_seq'::regclass);
 :   ALTER TABLE public.inter ALTER COLUMN "row" DROP DEFAULT;
       public          postgres    false    206    205            �
           2604    24817    messages id    DEFAULT     j   ALTER TABLE ONLY public.messages ALTER COLUMN id SET DEFAULT nextval('public.messages_id_seq'::regclass);
 :   ALTER TABLE public.messages ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    212    211            �
           2604    24761    notifications id    DEFAULT     t   ALTER TABLE ONLY public.notifications ALTER COLUMN id SET DEFAULT nextval('public.notifications_id_seq'::regclass);
 ?   ALTER TABLE public.notifications ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    210    209            ,          0    16403 
   activities 
   TABLE DATA           e   COPY public.activities (name, priority, creator_name, id, description, delegate, status) FROM stdin;
    public          postgres    false    204   �.       -          0    16411    inter 
   TABLE DATA           0   COPY public.inter ("row", name, id) FROM stdin;
    public          postgres    false    205   v4       3          0    24787    messages 
   TABLE DATA           K   COPY public.messages (activity, author, body, "timestamp", id) FROM stdin;
    public          postgres    false    211   �5       1          0    24744    notifications 
   TABLE DATA           M   COPY public.notifications ("timestamp", status, id, title, body) FROM stdin;
    public          postgres    false    209   �:       /          0    24621 
   user_roles 
   TABLE DATA           4   COPY public.user_roles (role, username) FROM stdin;
    public          postgres    false    207   �D       +          0    16395    users 
   TABLE DATA           0   COPY public.users (name, mail, psw) FROM stdin;
    public          postgres    false    203   F       A           0    0    activities_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.activities_id_seq', 14, true);
          public          postgres    false    208            B           0    0    inter_row_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.inter_row_seq', 6, true);
          public          postgres    false    206            C           0    0    messages_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.messages_id_seq', 1, true);
          public          postgres    false    212            D           0    0    notifications_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.notifications_id_seq', 1, false);
          public          postgres    false    210            �
           1259    24731    activities_id_idx    INDEX     M   CREATE UNIQUE INDEX activities_id_idx ON public.activities USING btree (id);
 %   DROP INDEX public.activities_id_idx;
       public            postgres    false    204            �
           1259    24644    name_key    INDEX     A   CREATE UNIQUE INDEX name_key ON public.users USING btree (name);
    DROP INDEX public.name_key;
       public            postgres    false    203            �
           2606    24668 '   activities activities_creator_name_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.activities
    ADD CONSTRAINT activities_creator_name_fkey FOREIGN KEY (creator_name) REFERENCES public.users(name);
 Q   ALTER TABLE ONLY public.activities DROP CONSTRAINT activities_creator_name_fkey;
       public          postgres    false    204    203    2725            �
           2606    24775 #   activities activities_delegate_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.activities
    ADD CONSTRAINT activities_delegate_fkey FOREIGN KEY (delegate) REFERENCES public.users(name);
 M   ALTER TABLE ONLY public.activities DROP CONSTRAINT activities_delegate_fkey;
       public          postgres    false    2725    203    204            �
           2606    24737    inter inter_activity_id_fkey    FK CONSTRAINT     {   ALTER TABLE ONLY public.inter
    ADD CONSTRAINT inter_activity_id_fkey FOREIGN KEY (id) REFERENCES public.activities(id);
 F   ALTER TABLE ONLY public.inter DROP CONSTRAINT inter_activity_id_fkey;
       public          postgres    false    205    2726    204            �
           2606    24684    inter inter_assigned_to_fkey    FK CONSTRAINT     z   ALTER TABLE ONLY public.inter
    ADD CONSTRAINT inter_assigned_to_fkey FOREIGN KEY (name) REFERENCES public.users(name);
 F   ALTER TABLE ONLY public.inter DROP CONSTRAINT inter_assigned_to_fkey;
       public          postgres    false    2725    203    205            �
           2606    24809    messages messages_activity_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_activity_fkey FOREIGN KEY (activity) REFERENCES public.activities(id);
 I   ALTER TABLE ONLY public.messages DROP CONSTRAINT messages_activity_fkey;
       public          postgres    false    211    2726    204            �
           2606    24801    messages messages_author_fkey    FK CONSTRAINT     }   ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_author_fkey FOREIGN KEY (author) REFERENCES public.users(name);
 G   ALTER TABLE ONLY public.messages DROP CONSTRAINT messages_author_fkey;
       public          postgres    false    211    203    2725            ,   "   fare money	URGENT	Elisa	11		\N	\N
 (   tosare le pecore	MEDIUM	Elisa	12		\N	\N
 ;   Marianna dice ciao e va in campagna	URGENT	Elisa	16		\N	\N
 '   andare a sciare	MEDIUM	Elisa	17		\N	\N
 $   tuffarsi a kiev	LOW	Elisa	18		\N	\N
 (   evitare le bombe	MEDIUM	Elisa	19		\N	\N
    fermarsi	URGENT	Elisa	5		\N	\N
 1   suonare il campanello	LOW	Paolo	3		Elisa	PENDING
 \   fare la visita aziendale	LOW	Elisa	4	lauraaaaa vienici a trovare, ho fame	Elisa	IN_PROGRESS
 9   vincere la premier di golf	MEDIUM	Elisa	6		Paolo	PENDING
 -   giocare a lol	URGENT	Elisa	7		Paolo	ACCEPTED
 :   installare i cheat su csgo	LOW	Elisa	9		Federico	ACCEPTED
 _   Admineggiare	URGENT	Angelo	13	angelo deve fare le sue cose<br><span></span>	Angelo	IN_PROGRESS
 �   suonare la trombetta	MEDIUM	Elisa	1	<span>maraaaaaaaaameeeeeeoooooooooo (li facciamo assieme gli integrali l'anno prossimo) :'(<br><span></span></span>	Elisa	PENDING
 �   fare i tortellini alla paulo dybala	URGENT	Elisa	8	<span><span><span><span><span><p><b>scimmia spagnola festa di sangue</b> asadasd<br></p></span></span></span></span></span>	Elisa	IN_PROGRESS
 T   gambling	URGENT	Elisa	14	ho scommesso 5000 euro yeah<span></span>	Elisa	IN_PROGRESS
 ,   Andare in vacanza	URGENT	Emanuele	20		\N	\N
 K   Attivazione banca 123	LOW	Federico	10	ohoh<span></span>	Angelo	IN_PROGRESS
 x   fare english the	LOW	Elisa	2	fare l'infuso con le ceneri della regina ELisabetta II<br><span></span>	Angelo	IN_PROGRESS
    \.


      -      11	Elisa	3
 
   4	Paolo	6
 
   5	Paolo	7
    15	Federico	9
    13	Elisa	4
    17	Angelo	13
    18	Elisa	14
    14	Elisa	8
 
   9	Elisa	1
    19	Paolo	4
    20	Angelo	2
    16	Angelo	10
    21	Paolo	14
    22	Angelo	14
    23	Federico	14
 
   24	Polo	2
    25	Paolo	2
 
   6	Elisa	2
    \.


      3   6   8	Elisa	ho messo su acqua	2024-09-17 17:51:18.80226	1
 7   8	Elisa	acqua evaporata :(	2024-09-19 13:51:18.80226	3
 >   8	Elisa	sei evaporato anche tu? 	2024-09-19 16:28:50.771843	4
 ?   8	Angelo	no scusa sono ancora qua	2024-09-20 11:56:01.146352	6
 1   8	Elisa	stai fermo!	2024-09-20 11:59:40.253884	7
 2   8	Elisa	yo yo yomino	2024-09-20 12:00:47.072706	8
 L   8	Angelo	la mucca mumu yeah ha fatto i mini mu	2024-09-20 12:14:21.075264	9
 H   8	Elisa	un budino al latte con le macchie	2024-09-20 12:17:43.918866	10
 B   8	Angelo	super goloso super cremoso	2024-09-20 12:19:22.823523	11
 D   8	Elisa	vaniglia choco choco vaniglia	2024-09-20 12:23:09.138468	12
 N   8	Angelo	ha fatto mumu la mucca con gli occhiali	2024-09-20 12:24:36.77448	13
 =   8	Elisa	mumu super goloso yeah	2024-09-20 12:32:10.939457	14
 B   8	Elisa	macchie golose è tutto ok!	2024-09-20 12:44:39.490271	15
 =   8	Angelo	mi hai rubato la fine	2024-09-20 12:49:07.801618	16
 E   8	Angelo	e non mi rispondi nemmeno\r\n	2024-09-20 13:06:17.477512	17
 2   8	Elisa	stupido\r\n	2024-09-20 15:51:52.029736	18
 >   14	Angelo	punta tutto sul rosso	2024-09-20 15:54:56.064262	19
 9   14	Elisa	siete tutti scemi	2024-09-20 15:55:18.803563	20
 _   2	Angelo	Mi raccomando leggere la descrizione che è importante!	2024-09-20 15:59:05.831548	21
    \.


      1   y   2024-09-17 17:58:10.53481	f	2	Activity creation	Created Name: evitare le bombe\nId: 19\nPriority: MEDIUM\nCreator: Elisa
    2024-09-18 15:10:29.192131	f	3	Activity assign	Assigned Name: fare english the\nId: 2\nPriority: LOW\nCreator: Elisa to Angelo
 �   2024-09-18 15:10:37.805624	f	4	Activity started	User Angelo started Name: Attivazione banca 123\nId: 10\nPriority: LOW\nCreator: Federico
 j   2024-09-19 16:39:26.117183	f	5	User creation	Created Name: toto\nMail: toto@gmail.com\nPassword: toto1234
 v   2024-09-19 16:44:12.140543	f	6	User creation	Created Name: simbiosi\nMail: simbiosi@gmail.com\nPassword: simbiosi1234
 p   2024-09-19 16:46:40.948054	f	7	User creation	Created Name: castro\nMail: castro@gmail.com\nPassword: castro1234
 l   2024-09-19 16:48:01.12164	f	8	User creation	Created Name: paola\nMail: paola@gmail.com\nPassword: paola1234
 m   2024-09-19 16:52:50.378184	f	9	User creation	Created Name: felpa\nMail: felpa@gmail.com\nPassword: felpa1234
 }   2024-09-19 17:28:01.887956	f	10	Activity started	User Elisa started Name: gambling\nId: 14\nPriority: URGENT\nCreator: Elisa
 �   2024-09-19 17:29:25.052712	t	11	Activity assigned	Name: gambling\nId: 14\nPriority: URGENT\nCreator: Elisa\nName: Paolo\nMail: paolo@gmail.com\nPassword: $2a$10$9eq.5XEIdeUS7MM4VLxsfuLATzBemWOtPbKrEXJxgaScmj.Z0aDQK
 _   2024-09-20 14:47:23.123447	f	12	User creation	Created Name: Emanuele\nMail: emanuele@gmail.com
 w   2024-09-20 14:47:55.532301	f	13	Activity creation	Name: Andare in vacanza\nId: 20\nPriority: URGENT\nCreator: Emanuele
 P   2024-09-20 14:49:31.354782	f	14	User created	Name: Ciao\nMail: vfhbjwkejwvcwjwv
 P   2024-09-20 14:49:36.936101	f	15	User deleted	Name: Ciao\nMail: vfhbjwkejwvcwjwv
 P   2024-09-20 15:52:24.304582	t	16	User deleted	Name: Ciao\nMail: vfhbjwkejwvcwjwv
 �   2024-09-20 15:54:21.717298	f	17	Activity assigned	Name: gambling\nId: 14\nPriority: URGENT\nCreator: Elisa\nName: Angelo\nMail: angelo@gmail.com
 �   2024-09-20 15:57:40.751682	f	18	Activity assigned	Name: gambling\nId: 14\nPriority: URGENT\nCreator: Elisa\nName: Federico\nMail: taargafederico01@gmail.com
 �   2024-09-20 15:58:12.453495	f	19	Activity started	User Angelo started Name: fare english the\nId: 2\nPriority: LOW\nCreator: Elisa
 �   2024-09-20 15:59:48.753003	f	20	Activity assigned	Name: fare english the\nId: 2\nPriority: LOW\nCreator: Elisa\nName: Polo\nMail: polo@gmai.com
 �   2024-09-20 16:00:03.262833	f	21	Activity assigned	Name: fare english the\nId: 2\nPriority: LOW\nCreator: Elisa\nName: Paolo\nMail: paolo@gmail.com
    \.


      /      USER	pergola
 
   USER	Polo
    USER	Federico
    OWNER	Angelo
    ADMIN	Angelo
    USER	Angelo
    USER	mouse
    USER	ursula
    USER	Laura
    USER	simbiosi
    USER	castro
    USER	paola
    USER	felpa
    USER	Emanuele
 
   USER	Ciao
    \.


      +   S   Paolo	paolo@gmail.com	$2a$10$9eq.5XEIdeUS7MM4VLxsfuLATzBemWOtPbKrEXJxgaScmj.Z0aDQK
 S   Stino	stino@gmail.com	$2a$10$vYjlREcvD4cwsC6jyXKqMOTu3wwmHSYg94EVEbJCDdS9rkYIG2ZWG
 S   Elisa	elisa@gmail.com	$2a$10$g6fpRzMe/5qDKbLmTFJZBuESQUhnhMvsECgTmGE7rzrlFG4zu4tA2
 W   pergola	pergola@gmail.com	$2a$10$MC5BCj5y1FzibUvZMiqsz.RN3GbaavcHCFtFi7fvhE0c3DcOnROQ2
 U   Angelo	angelo@gmail.com	$2a$10$Y0hwuAlJgky29xRO2zWHIedaxlOngxrxmG1V7F3.sJUgvx8/XR3jy
 a   Federico	taargafederico01@gmail.com	$2a$10$vJbUhsdDD8piZLFBedizAO6BJD4dT6oRRh2gJYk8NsQue7asmHaA6
 P   Polo	polo@gmai.com	$2a$10$WCHhaSBmKStkBE2pQM938eHu0lflkWw1GaflDq1jmDNOX6FvTOZ.y
 V   mouse	mouse@tastiera.com	$2a$10$GZS3/e9ZWwnEIqb9M26SV.v8zz/cCotqqve3Hx/cg7dcCJVrxdJ7u
 T   ursula	ursula@anto.com	$2a$10$f.26y92WNIH3Me.fRAtFuewnUIFAcc1o0yuWvY.PNCiERwCA1.nJy
 S   Laura	laura@gmail.com	$2a$10$h.zScWfhN.b8ruRi2xcc2OEA1oKbrSEe96knlqzHbv.umpK2Bu.6y
 Y   simbiosi	simbiosi@gmail.com	$2a$10$3SHyXYZQr2.qrdJCtVufUuIYa3xlrGDwC/UouHWL0dkMpTP3AadZ.
 S   paola	paola@gmail.com	$2a$10$XRrx9N5Ae64GQjVl9.m7NeXGYGp8oc1Zw0vbBiJIcrwG2LsPJOHD6
 S   felpa	felpa@gmail.com	$2a$10$8VnTEy4wkba44wvJUcwmsuTjP9wSGWs6yl2oCQq9ziqRMXjqgYSfy
 Y   Emanuele	emanuele@gmail.com	$2a$10$/vJeI9ZlqYZ6Aj0OpEriTuCFBuEww09bfnm/fdjLTmhhRuxnBekRS
    \.


     