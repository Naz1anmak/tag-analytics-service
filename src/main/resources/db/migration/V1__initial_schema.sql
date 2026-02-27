create extension if not exists "pgcrypto";

CREATE TABLE IF NOT EXISTS tag_stats (
    tag_id UUID PRIMARY KEY NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);
CREATE INDEX IF NOT EXISTS idx_tag_stats_created_at ON tag_stats(created_at);
