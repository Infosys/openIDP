-- Table: public.resource_attribute

-- DROP TABLE public.resource_attribute;

CREATE TABLE public.resource_attribute
(
    id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    value character varying(255) COLLATE pg_catalog."default",
    resource_id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT res_attr_pk PRIMARY KEY (id),
    CONSTRAINT fk_5hrm2vlf9ql5fu022kqepovbr FOREIGN KEY (resource_id)
        REFERENCES public.resource_server_resource (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.resource_attribute
    OWNER to postgres;
	
UPDATE public.databasechangelog SET md5sum='7:57960fc0b0f0dd0563ea6f8b2e4a1707' where id='authz-4.0.0.CR1';