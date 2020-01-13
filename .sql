CREATE TABLE"someone"(
	"id"serial8 PRIMARY KEY,
	"nickname"VARCHAR,
	"email"VARCHAR UNIQUE,
	"shadow"VARCHAR,
	"role"VARCHAR NOT NULL,
	"verified"bool DEFAULT'0'
);
COMMENT ON COLUMN"someone"."id"IS'主鍵';
COMMENT ON COLUMN"someone"."nickname"IS'暱稱';
COMMENT ON COLUMN"someone"."email"IS'電子郵件信箱';
COMMENT ON COLUMN"someone"."shadow"IS'密碼';
COMMENT ON COLUMN"someone"."role"IS'權限';
COMMENT ON COLUMN"someone"."verified"IS'已啟用';
COMMENT ON TABLE"someone"IS'使用者';

CREATE TABLE"email_verification"(
	"id"serial8 PRIMARY KEY,
	"verification_code"VARCHAR NOT NULL UNIQUE,
	"someone"int8 REFERENCES"someone"("id")ON UPDATE CASCADE ON DELETE RESTRICT,
	"expiry"timestamp
);
COMMENT ON COLUMN"email_verification"."id"IS'主鍵';
COMMENT ON COLUMN"email_verification"."verification_code"IS'驗證碼';
COMMENT ON COLUMN"email_verification"."someone"IS'使用者';
COMMENT ON COLUMN"email_verification"."expiry"IS'驗證碼逾期時戳';
COMMENT ON TABLE"email_verification"IS'驗證表';