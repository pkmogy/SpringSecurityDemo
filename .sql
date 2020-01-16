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

CREATE TABLE acl_class(
    "id"serial8 NOT NULL PRIMARY KEY,
    "class"VARCHAR NOT NULL UNIQUE 
);
COMMENT ON COLUMN"acl_class"."id"IS'主鍵';
COMMENT ON COLUMN"acl_class"."class"IS'Class路徑';
COMMENT ON TABLE"acl_class"IS'受保護的Class';

CREATE TABLE acl_sid(
    "id"serial8 NOT NULL PRIMARY KEY,
    "principal"BOOLEAN NOT NULL,
    "sid"VARCHAR NOT NULL,
	UNIQUE("sid","principal")
);
COMMENT ON COLUMN"acl_sid"."id"IS'主鍵';
COMMENT ON COLUMN"acl_sid"."principal"IS'是否是用戶';
COMMENT ON COLUMN"acl_sid"."sid"IS'權限名稱 or UserName';
COMMENT ON TABLE"acl_sid"IS'權限';

CREATE TABLE acl_object_identity(
    "id"serial8 PRIMARY KEY,
    "object_id_class"int8 NOT NULL REFERENCES acl_class("id"),
    object_id_identity VARCHAR(36) NOT NULL,
    parent_object BIGINT REFERENCES acl_object_identity("id"),
    owner_sid BIGINT REFERENCES acl_sid("id"),
    entries_inheriting BOOLEAN NOT NULL,
	UNIQUE("object_id_class","object_id_identity")
);
COMMENT ON COLUMN"acl_object_identity"."id"IS'主鍵';
COMMENT ON COLUMN"acl_object_identity"."object_id_class"IS'持久層Class';
COMMENT ON COLUMN"acl_object_identity"."object_id_identity"IS'持久層Class主鍵';
COMMENT ON COLUMN"acl_object_identity"."parent_object"IS'父層';
COMMENT ON COLUMN"acl_object_identity"."owner_sid"IS'權限';
COMMENT ON COLUMN"acl_object_identity"."entries_inheriting"IS'是否繼承父層權限';
COMMENT ON TABLE"acl_object_identity"IS'權限身分';

CREATE TABLE acl_entry(
    "id"serial8 PRIMARY KEY,
    "acl_object_identity"int8 NOT NULL REFERENCES acl_object_identity("id"),
    "ace_order"int4 NOT NULL,
    "sid"int8 NOT NULL REFERENCES acl_sid("id"),
    "mask"int8 NOT NULL,
    "granting"BOOLEAN NOT NULL,
    "audit_success"BOOLEAN NOT NULL,
    "audit_failure"BOOLEAN NOT NULL,
	UNIQUE("acl_object_identity","ace_order")
);
COMMENT ON COLUMN"acl_entry"."id"IS'主鍵';
COMMENT ON COLUMN"acl_entry"."acl_object_identity"IS'權限身分';
COMMENT ON COLUMN"acl_entry"."ace_order"IS'權限排序';
COMMENT ON COLUMN"acl_entry"."sid"IS'權限';
COMMENT ON COLUMN"acl_entry"."mask"IS'掩碼';
COMMENT ON COLUMN"acl_entry"."granting"IS'是否授權';
COMMENT ON COLUMN"acl_entry"."audit_success"IS'審計目的';
COMMENT ON COLUMN"acl_entry"."audit_failure"IS'審計目的';
COMMENT ON TABLE"acl_entry"IS'權限分配';