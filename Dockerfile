FROM postgres:11.16

COPY init.sql /docker-entrypoint-initdb.d/

ENV POSTGRES_PASSWORD=postgres

EXPOSE 5432