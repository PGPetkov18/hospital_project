USE [master]
GO
/****** Object:  Database [Hospital]    Script Date: 4/27/2023 11:15:18 AM ******/
CREATE DATABASE [Hospital]
    CONTAINMENT = NONE
    ON  PRIMARY
    ( NAME = N'Hospital', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Hospital.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
    LOG ON
    ( NAME = N'Hospital_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Hospital_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [Hospital] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
    begin
        EXEC [Hospital].[dbo].[sp_fulltext_database] @action = 'enable'
    end
GO
ALTER DATABASE [Hospital] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [Hospital] SET ANSI_NULLS OFF
GO
ALTER DATABASE [Hospital] SET ANSI_PADDING OFF
GO
ALTER DATABASE [Hospital] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [Hospital] SET ARITHABORT OFF
GO
ALTER DATABASE [Hospital] SET AUTO_CLOSE OFF
GO
ALTER DATABASE [Hospital] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [Hospital] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [Hospital] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [Hospital] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [Hospital] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [Hospital] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [Hospital] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [Hospital] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [Hospital] SET  DISABLE_BROKER
GO
ALTER DATABASE [Hospital] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [Hospital] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [Hospital] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [Hospital] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [Hospital] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [Hospital] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [Hospital] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [Hospital] SET RECOVERY SIMPLE
GO
ALTER DATABASE [Hospital] SET  MULTI_USER
GO
ALTER DATABASE [Hospital] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [Hospital] SET DB_CHAINING OFF
GO
ALTER DATABASE [Hospital] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF )
GO
ALTER DATABASE [Hospital] SET TARGET_RECOVERY_TIME = 60 SECONDS
GO
ALTER DATABASE [Hospital] SET DELAYED_DURABILITY = DISABLED
GO
ALTER DATABASE [Hospital] SET ACCELERATED_DATABASE_RECOVERY = OFF
GO
ALTER DATABASE [Hospital] SET QUERY_STORE = OFF
GO
USE [Hospital]
GO
/****** Object:  User [Batman]    Script Date: 4/27/2023 11:15:18 AM ******/
CREATE USER [Batman] FOR LOGIN [Shef] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [Batman]
GO
ALTER ROLE [db_accessadmin] ADD MEMBER [Batman]
GO
ALTER ROLE [db_securityadmin] ADD MEMBER [Batman]
GO
ALTER ROLE [db_datareader] ADD MEMBER [Batman]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [Batman]
GO
/****** Object:  Table [dbo].[Addresses]    Script Date: 4/27/2023 11:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Addresses](
                                  [Id] [varchar](36) NOT NULL,
                                  [Country] [nvarchar](100) NOT NULL,
                                  [City] [nvarchar](100) NOT NULL,
                                  [Street] [nvarchar](100) NOT NULL,
                                  [Number] [int] NOT NULL,
                                  PRIMARY KEY CLUSTERED
                                      (
                                       [Id] ASC
                                          )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DischargeSummaries]    Script Date: 4/27/2023 11:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DischargeSummaries](
                                           [Id] [varchar](36) NOT NULL,
                                           [FileName] [varchar](255) NOT NULL,
                                           [FileEntity] [varbinary](max) NOT NULL,
                                           [DoctorId] [varchar](36) NOT NULL,
                                           [PatientId] [varchar](36) NOT NULL,
                                           CONSTRAINT [PK__Discharg__3214EC07E1247BCB] PRIMARY KEY CLUSTERED
                                               (
                                                [Id] ASC
                                                   )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Doctors]    Script Date: 4/27/2023 11:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Doctors](
                                [Id] [varchar](36) NOT NULL,
                                [Specialization] [nvarchar](70) NOT NULL,
                                [UserId] [varchar](36) NOT NULL,
                                PRIMARY KEY CLUSTERED
                                    (
                                     [Id] ASC
                                        )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
                                UNIQUE NONCLUSTERED
                                    (
                                     [UserId] ASC
                                        )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Hospitals]    Script Date: 4/27/2023 11:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Hospitals](
                                  [Id] [varchar](36) NOT NULL,
                                  [Name] [nvarchar](150) NOT NULL,
                                  [AddressId] [varchar](36) NOT NULL,
                                  PRIMARY KEY CLUSTERED
                                      (
                                       [Id] ASC
                                          )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
                                  UNIQUE NONCLUSTERED
                                      (
                                       [AddressId] ASC
                                          )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Medicines]    Script Date: 4/27/2023 11:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Medicines](
                                  [Id] [varchar](36) NOT NULL,
                                  [Name] [nvarchar](70) NOT NULL,
                                  [Quantity] [int] NOT NULL,
                                  [Description] [nvarchar](max) NOT NULL,
                                  [DoctorId] [varchar](36) NOT NULL,
                                  CONSTRAINT [PK__Medicine__3214EC0790875C5A] PRIMARY KEY CLUSTERED
                                      (
                                       [Id] ASC
                                          )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Patients]    Script Date: 4/27/2023 11:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Patients](
                                 [Id] [varchar](36) NOT NULL,
                                 [DateOfBirth] [date] NOT NULL,
                                 [Condition] [nvarchar](255) NOT NULL,
                                 [SurgeryRequired] [bit] NOT NULL,
                                 [HospitalizationDuration] [smallint] NOT NULL,
                                 [UserId] [varchar](36) NOT NULL,
                                 PRIMARY KEY CLUSTERED
                                     (
                                      [Id] ASC
                                         )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
                                 UNIQUE NONCLUSTERED
                                     (
                                      [UserId] ASC
                                         )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PatientsMedicines]    Script Date: 4/27/2023 11:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PatientsMedicines](
                                          [PatientId] [varchar](36) NOT NULL,
                                          [MedicineId] [varchar](36) NOT NULL,
                                          PRIMARY KEY CLUSTERED
                                              (
                                               [PatientId] ASC,
                                               [MedicineId] ASC
                                                  )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Receptionists]    Script Date: 4/27/2023 11:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Receptionists](
                                      [Id] [varchar](36) NOT NULL,
                                      [UserId] [varchar](36) NOT NULL,
                                      PRIMARY KEY CLUSTERED
                                          (
                                           [Id] ASC
                                              )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
                                      UNIQUE NONCLUSTERED
                                          (
                                           [UserId] ASC
                                              )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Relatives]    Script Date: 4/27/2023 11:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Relatives](
                                  [Id] [varchar](36) NOT NULL,
                                  [AddressId] [varchar](36) NOT NULL,
                                  [FirstName] [nvarchar](100) NOT NULL,
                                  [LastName] [nvarchar](100) NOT NULL,
                                  [Username] [varchar](100) NOT NULL,
                                  [PatientId] [varchar](36) NOT NULL,
                                  CONSTRAINT [PK__Relative__3214EC0721428E16] PRIMARY KEY CLUSTERED
                                      (
                                       [Id] ASC
                                          )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
                                  CONSTRAINT [UQ_AddressId_Relatives] UNIQUE NONCLUSTERED
                                      (
                                       [AddressId] ASC
                                          )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Sessions]    Script Date: 4/27/2023 11:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Sessions](
                                 [Id] [varchar](36) NOT NULL,
                                 [RoleName] [varchar](20) NOT NULL,
                                 [TimeCreated] [datetime2](0) NOT NULL,
                                 [UserId] [varchar](36) NOT NULL,
                                 PRIMARY KEY CLUSTERED
                                     (
                                      [Id] ASC
                                         )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
                                 UNIQUE NONCLUSTERED
                                     (
                                      [UserId] ASC
                                         )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Treatments]    Script Date: 4/27/2023 11:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Treatments](
                                   [Id] [varchar](36) NOT NULL,
                                   [Name] [nvarchar](255) NOT NULL,
                                   [DateOfTreatment] [datetime2](0) NOT NULL,
                                   [Description] [nvarchar](max) NOT NULL,
                                   [PatientId] [varchar](36) NOT NULL,
                                   [DoctorId] [varchar](36) NOT NULL,
                                   CONSTRAINT [PK__Treatmen__3214EC078D8B17D7] PRIMARY KEY CLUSTERED
                                       (
                                        [Id] ASC
                                           )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 4/27/2023 11:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
                              [Id] [varchar](36) NOT NULL,
                              [Username] [nvarchar](150) NOT NULL,
                              [Password] [varchar](600) NOT NULL,
                              [FirstName] [nvarchar](100) NOT NULL,
                              [LastName] [nvarchar](100) NOT NULL,
                              [IsAdmin] [bit] NOT NULL,
                              [salt] [varbinary](16) NOT NULL,
                              [HospitalId] [varchar](36) NOT NULL,
                              PRIMARY KEY CLUSTERED
                                  (
                                   [Id] ASC
                                      )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
                              UNIQUE NONCLUSTERED
                                  (
                                   [Username] ASC
                                      )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Visits]    Script Date: 4/27/2023 11:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Visits](
                               [Id] [varchar](36) NOT NULL,
                               [PatientId] [varchar](36) NOT NULL,
                               [RelativeId] [varchar](36) NOT NULL,
                               [VisitTime] [datetime2](0) NOT NULL,
                               [IsApproved] [bit] NOT NULL,
                               [DoctorId] [varchar](36) NOT NULL,
                               PRIMARY KEY CLUSTERED
                                   (
                                    [Id] ASC
                                       )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Users] ADD  DEFAULT ((0)) FOR [IsAdmin]
GO
ALTER TABLE [dbo].[DischargeSummaries]  WITH CHECK ADD  CONSTRAINT [FK__Discharge__Docto__44FF419A] FOREIGN KEY([DoctorId])
    REFERENCES [dbo].[Doctors] ([Id])
GO
ALTER TABLE [dbo].[DischargeSummaries] CHECK CONSTRAINT [FK__Discharge__Docto__44FF419A]
GO
ALTER TABLE [dbo].[DischargeSummaries]  WITH CHECK ADD  CONSTRAINT [FK__Discharge__Patie__45F365D3] FOREIGN KEY([PatientId])
    REFERENCES [dbo].[Patients] ([Id])
GO
ALTER TABLE [dbo].[DischargeSummaries] CHECK CONSTRAINT [FK__Discharge__Patie__45F365D3]
GO
ALTER TABLE [dbo].[Doctors]  WITH CHECK ADD FOREIGN KEY([UserId])
    REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Hospitals]  WITH CHECK ADD FOREIGN KEY([AddressId])
    REFERENCES [dbo].[Addresses] ([Id])
GO
ALTER TABLE [dbo].[Patients]  WITH CHECK ADD FOREIGN KEY([UserId])
    REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[PatientsMedicines]  WITH CHECK ADD  CONSTRAINT [FK__PatientsM__Medic__49C3F6B7] FOREIGN KEY([MedicineId])
    REFERENCES [dbo].[Medicines] ([Id])
GO
ALTER TABLE [dbo].[PatientsMedicines] CHECK CONSTRAINT [FK__PatientsM__Medic__49C3F6B7]
GO
ALTER TABLE [dbo].[PatientsMedicines]  WITH CHECK ADD FOREIGN KEY([PatientId])
    REFERENCES [dbo].[Patients] ([Id])
GO
ALTER TABLE [dbo].[Receptionists]  WITH CHECK ADD FOREIGN KEY([UserId])
    REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Relatives]  WITH CHECK ADD  CONSTRAINT [FK__Relatives__Addre__4F7CD00D] FOREIGN KEY([AddressId])
    REFERENCES [dbo].[Addresses] ([Id])
GO
ALTER TABLE [dbo].[Relatives] CHECK CONSTRAINT [FK__Relatives__Addre__4F7CD00D]
GO
ALTER TABLE [dbo].[Relatives]  WITH CHECK ADD  CONSTRAINT [FK__Relatives__PatientI__5441852A] FOREIGN KEY([PatientId])
    REFERENCES [dbo].[Patients] ([Id])
GO
ALTER TABLE [dbo].[Relatives] CHECK CONSTRAINT [FK__Relatives__PatientI__5441852A]
GO
ALTER TABLE [dbo].[Sessions]  WITH CHECK ADD FOREIGN KEY([UserId])
    REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Treatments]  WITH CHECK ADD  CONSTRAINT [FK__Treatment__Patie__5070F446] FOREIGN KEY([PatientId])
    REFERENCES [dbo].[Patients] ([Id])
GO
ALTER TABLE [dbo].[Treatments] CHECK CONSTRAINT [FK__Treatment__Patie__5070F446]
GO
ALTER TABLE [dbo].[Treatments]  WITH CHECK ADD  CONSTRAINT [FK_Treatments_Doctors] FOREIGN KEY([DoctorId])
    REFERENCES [dbo].[Doctors] ([Id])
GO
ALTER TABLE [dbo].[Treatments] CHECK CONSTRAINT [FK_Treatments_Doctors]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD FOREIGN KEY([HospitalId])
    REFERENCES [dbo].[Hospitals] ([Id])
GO
ALTER TABLE [dbo].[Visits]  WITH CHECK ADD  CONSTRAINT [FK__Visits__PatientI__5441852A] FOREIGN KEY([PatientId])
    REFERENCES [dbo].[Patients] ([Id])
GO
ALTER TABLE [dbo].[Visits] CHECK CONSTRAINT [FK__Visits__PatientI__5441852A]
GO
ALTER TABLE [dbo].[Visits]  WITH CHECK ADD  CONSTRAINT [FK_Visits_Doctors__6969420] FOREIGN KEY([DoctorId])
    REFERENCES [dbo].[Doctors] ([Id])
GO
ALTER TABLE [dbo].[Visits] CHECK CONSTRAINT [FK_Visits_Doctors__6969420]
GO
ALTER TABLE [dbo].[Visits]  WITH CHECK ADD  CONSTRAINT [FK_Visits_Relatives] FOREIGN KEY([RelativeId])
    REFERENCES [dbo].[Relatives] ([Id])
GO
ALTER TABLE [dbo].[Visits] CHECK CONSTRAINT [FK_Visits_Relatives]
GO
ALTER TABLE [dbo].[Addresses]  WITH CHECK ADD CHECK  (([Number]>(0)))
GO
ALTER TABLE [dbo].[Sessions]  WITH CHECK ADD CHECK  (([RoleName]='admin' OR [RoleName]='patient' OR [RoleName]='relative' OR [RoleName]='receptionist' OR [RoleName]='doctor'))
GO
/****** Object:  StoredProcedure [dbo].[GetVisitsByUserId]    Script Date: 4/27/2023 11:15:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GetVisitsByUserId]
@userId varchar(50)
AS
BEGIN
    SET NOCOUNT ON;

    SELECT
        v.Id,
        v.PatientId,
        v.RelativeId,
        v.VisitTime,
        v.DoctorId,
        v.IsApproved
    FROM Visits v
             INNER JOIN Patients p
                        ON p.Id = v.PatientId
             INNER JOIN Users u
                        ON u.Id = p.UserId
    WHERE u.Id = @userId

    UNION ALL

    SELECT
        v.Id,
        v.PatientId,
        v.RelativeId,
        v.VisitTime,
        v.DoctorId,
        v.IsApproved
    FROM Visits v
             INNER JOIN Doctors d
                        ON d.Id = v.DoctorId
    WHERE d.UserId = @userId

END
GO
USE [master]
GO
ALTER DATABASE [Hospital] SET  READ_WRITE
GO