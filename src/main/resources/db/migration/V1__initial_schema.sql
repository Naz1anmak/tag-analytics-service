create extension if not exists "pgcrypto";

CREATE TABLE IF NOT EXISTS tag_stats (
    tag_id UUID PRIMARY KEY NOT NULL,
    usage_count BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX IF NOT EXISTS idx_tag_stats_created_at ON tag_stats(created_at);
