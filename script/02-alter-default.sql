-- created_at / updated_at default 설정

ALTER TABLE hub_db.p_hubs
    ALTER COLUMN created_at SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE hub_db.p_hubs
    ALTER COLUMN updated_at SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE hub_db.p_hub_routes
    ALTER COLUMN created_at SET DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE hub_db.p_hub_routes
    ALTER COLUMN updated_at SET DEFAULT CURRENT_TIMESTAMP;