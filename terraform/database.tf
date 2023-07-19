module "rds" {
  source  = "terraform-aws-modules/rds/aws"
  version = "6.1.0"

  identifier = "beerstore-rds"

  engine               = "postgres"
  engine_version       = "14"
  family               = "postgres14" # DB parameter group
  major_engine_version = "14"         # DB option group
  instance_class       = "db.t3.micro"

  allocated_storage     = 20
  max_allocated_storage = 25

  db_name  = "beerstore"
  username = "beerstore"
  password = "beerstore"
  port     = 5432

  multi_az               = false

  maintenance_window              = "Mon:00:00-Mon:03:00"
  backup_window                   = "03:00-06:00"
  storage_type = "gp2"

  vpc_security_group_ids = [aws_security_group.database.id]
  subnet_ids = flatten(chunklist(aws_subnet.private_subnet.*.id, 1))

  db_subnet_group_name = aws_db_subnet_group.databases_group.name


#  db_subnet_group_name   = module.vpc.database_subnet_group
#  vpc_security_group_ids = [module.security_group.security_group_id]

#  enabled_cloudwatch_logs_exports = ["postgresql", "upgrade"]
#  create_cloudwatch_log_group     = true

#  backup_retention_period = 1
#  skip_final_snapshot     = true
#  deletion_protection     = false

#  performance_insights_enabled          = true
#  performance_insights_retention_period = 7
#  create_monitoring_role                = true
#  monitoring_interval                   = 60
#  monitoring_role_name                  = "example-monitoring-role-name"
#  monitoring_role_use_name_prefix       = true
#  monitoring_role_description           = "Description for monitoring role"


}