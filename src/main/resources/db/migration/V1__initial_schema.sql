create extension if not exists "pgcrypto";

CREATE TABLE IF NOT EXISTS tag_stats (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    version BIGINT NOT NULL,
    tag_id UUID NOT NULL UNIQUE,
    usage_count BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX IF NOT EXISTS idx_tag_stats_created_at ON tag_stats(created_at);
