-- =============================================================================
-- LT10: Initial roles and users for Keycloak (realm: internship-realm)
-- =============================================================================

-- 1. Create 3 Realm Roles: ROLE_ADMIN, ROLE_TEACHER, ROLE_USER
INSERT INTO keycloak_role (
    id,
    client_realm_constraint,
    client_role,
    description,
    name,
    realm_id,
    client,
    realm
) VALUES
      (
          '11111111-1111-1111-1111-111111111101',
          '101710c9-a4b6-45cd-af54-1cad04495dab',
          false,
          'Administrator role with full access',
          'ROLE_ADMIN',
          '101710c9-a4b6-45cd-af54-1cad04495dab',
          NULL,
          '101710c9-a4b6-45cd-af54-1cad04495dab'
      ),
      (
          '11111111-1111-1111-1111-111111111102',
          '101710c9-a4b6-45cd-af54-1cad04495dab',
          false,
          'Teacher role for managing courses and lessons',
          'ROLE_TEACHER',
          '101710c9-a4b6-45cd-af54-1cad04495dab',
          NULL,
          '101710c9-a4b6-45cd-af54-1cad04495dab'
      ),
      (
          '11111111-1111-1111-1111-111111111103',
          '101710c9-a4b6-45cd-af54-1cad04495dab',
          false,
          'Student/User role with read access',
          'ROLE_USER',
          '101710c9-a4b6-45cd-af54-1cad04495dab',
          NULL,
          '101710c9-a4b6-45cd-af54-1cad04495dab'
      )
    ON CONFLICT (id) DO NOTHING;

-- 2. Create Initial Users (admin_user, teacher_user, student_user)
INSERT INTO user_entity (
    id,
    email,
    email_constraint,
    email_verified,
    enabled,
    first_name,
    last_name,
    realm_id,
    username,
    created_timestamp,
    not_before
) VALUES
      (
          '22222222-2222-2222-2222-222222222201',
          'admin@bitlab.kz',
          'admin@bitlab.kz',
          true,
          true,
          'System',
          'Admin',
          '101710c9-a4b6-45cd-af54-1cad04495dab',
          'admin_user',
          EXTRACT(EPOCH FROM NOW())::bigint * 1000,
          0
      ),
      (
          '22222222-2222-2222-2222-222222222202',
          'teacher@bitlab.kz',
          'teacher@bitlab.kz',
          true,
          true,
          'Main',
          'Teacher',
          '101710c9-a4b6-45cd-af54-1cad04495dab',
          'teacher_user',
          EXTRACT(EPOCH FROM NOW())::bigint * 1000,
          0
      ),
      (
          '22222222-2222-2222-2222-222222222203',
          'student@bitlab.kz',
          'student@bitlab.kz',
          true,
          true,
          'Regular',
          'Student',
          '101710c9-a4b6-45cd-af54-1cad04495dab',
          'student_user',
          EXTRACT(EPOCH FROM NOW())::bigint * 1000,
          0
      )
    ON CONFLICT (id) DO NOTHING;

-- 3. Set Passwords in credential table (password for all test users: 12345)
    id,
    type,
    user_id,
    created_date,
    secret_data,
    credential_data,
    priority
) VALUES
      (
          '33333333-3333-3333-3333-333333333301',
          'password',
          '22222222-2222-2222-2222-222222222201',
          EXTRACT(EPOCH FROM NOW())::bigint * 1000,
          '{"value":"nTdKyJcNnrG7UtbzLUPrU2582lg637oFnuAyh4bXksc=","salt":"fAJybhzh7GsJaZ8TN0fqUw==","additionalParameters":{}}',
          '{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}',
          10
      ),
      (
          '33333333-3333-3333-3333-333333333302',
          'password',
          '22222222-2222-2222-2222-222222222202',
          EXTRACT(EPOCH FROM NOW())::bigint * 1000,
          '{"value":"nTdKyJcNnrG7UtbzLUPrU2582lg637oFnuAyh4bXksc=","salt":"fAJybhzh7GsJaZ8TN0fqUw==","additionalParameters":{}}',
          '{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}',
          10
      ),
      (
          '33333333-3333-3333-3333-333333333303',
          'password',
          '22222222-2222-2222-2222-222222222203',
          EXTRACT(EPOCH FROM NOW())::bigint * 1000,
          '{"value":"nTdKyJcNnrG7UtbzLUPrU2582lg637oFnuAyh4bXksc=","salt":"fAJybhzh7GsJaZ8TN0fqUw==","additionalParameters":{}}',
          '{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}',
          10
      )
    ON CONFLICT (id) DO NOTHING;

-- 4. Assign Roles to Users (user_role_mapping)
INSERT INTO user_role_mapping (role_id, user_id) VALUES
                                                     ('11111111-1111-1111-1111-111111111101', '22222222-2222-2222-2222-222222222201'), -- admin_user -> ROLE_ADMIN
                                                     ('11111111-1111-1111-1111-111111111102', '22222222-2222-2222-2222-222222222202'), -- teacher_user -> ROLE_TEACHER
                                                     ('11111111-1111-1111-1111-111111111103', '22222222-2222-2222-2222-222222222203')  -- student_user -> ROLE_USER
    ON CONFLICT DO NOTHING;


